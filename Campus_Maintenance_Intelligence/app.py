import os
from collections import Counter
from datetime import datetime, timezone
from pathlib import Path

import firebase_admin
from firebase_admin import credentials, firestore
from flask import Flask, jsonify, render_template, request
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity

BASE = Path(__file__).resolve().parent
SECRET = BASE / 'serviceAccountKey.json'
if not SECRET.exists():
    raise RuntimeError('Missing serviceAccountKey.json. See README.md for Firebase setup.')
firebase_admin.initialize_app(credentials.Certificate(str(SECRET)))
db = firestore.client()
app = Flask(__name__)
COLLECTION = db.collection('issues')
CATEGORIES = {
    'Electrical': ['light', 'fan', 'power', 'switch', 'wire', 'socket', 'electric'],
    'Computers': ['computer', 'pc', 'keyboard', 'mouse', 'monitor', 'projector', 'printer'],
    'Plumbing': ['water', 'tap', 'pipe', 'leak', 'toilet', 'washroom'],
    'Furniture': ['chair', 'desk', 'table', 'bench', 'door', 'window'],
    'Network': ['wifi', 'internet', 'network', 'router', 'connection'],
}
STATUSES = {'Open', 'In Progress', 'Resolved'}


def classify(text):
    words = set(text.lower().split())
    scores = {category: sum(1 for term in terms if term in words or term in text.lower())
              for category, terms in CATEGORIES.items()}
    best = max(scores, key=scores.get)
    return best if scores[best] else 'Other'


def serialize(doc):
    item = doc.to_dict()
    item['id'] = doc.id
    created = item.get('created_at')
    item['created_at'] = created.isoformat() if created else ''
    return item


def get_issues():
    return [serialize(doc) for doc in COLLECTION.stream()]


@app.get('/')
def home():
    return render_template('index.html')


@app.get('/api/issues')
def list_issues():
    return jsonify(sorted(get_issues(), key=lambda x: x.get('created_at', ''), reverse=True))


@app.post('/api/issues')
def create_issue():
    payload = request.get_json(silent=True) or {}
    title = str(payload.get('title', '')).strip()[:120]
    description = str(payload.get('description', '')).strip()[:1000]
    location = str(payload.get('location', '')).strip()[:120]
    if not all((title, description, location)):
        return jsonify(error='Title, description, and location are required.'), 400
    if len(title) < 5 or len(description) < 10:
        return jsonify(error='Please provide a descriptive title and at least 10 characters of detail.'), 400
    existing = get_issues()
    same_location = [x for x in existing if x.get('location', '').casefold() == location.casefold()
                     and x.get('status') != 'Resolved']
    matches = []
    if same_location:
        texts = [title + ' ' + description] + [x['title'] + ' ' + x['description'] for x in same_location]
        matrix = TfidfVectorizer(stop_words='english').fit_transform(texts)
        similarities = cosine_similarity(matrix[0], matrix[1:]).ravel()
        matches = [{'id': x['id'], 'title': x['title'], 'score': round(float(score), 2)}
                   for x, score in zip(same_location, similarities) if score >= 0.45]
        matches.sort(key=lambda x: x['score'], reverse=True)
    item = {'title': title, 'description': description, 'location': location,
            'category': classify(title + ' ' + description), 'status': 'Open',
            'created_at': datetime.now(timezone.utc)}
    ref = COLLECTION.document()
    ref.set(item)
    return jsonify({'id': ref.id, 'category': item['category'], 'possible_duplicates': matches[:3]}), 201


@app.patch('/api/issues/<issue_id>')
def update_issue(issue_id):
    payload = request.get_json(silent=True) or {}
    status = payload.get('status')
    if status not in STATUSES:
        return jsonify(error='Invalid status.'), 400
    ref = COLLECTION.document(issue_id)
    if not ref.get().exists:
        return jsonify(error='Issue not found.'), 404
    ref.update({'status': status})
    return jsonify(ok=True)


@app.get('/api/analytics')
def analytics():
    issues = get_issues()
    return jsonify(total=len(issues), by_status=dict(Counter(x['status'] for x in issues)),
                   by_category=dict(Counter(x['category'] for x in issues)),
                   by_location=dict(Counter(x['location'] for x in issues)))


if __name__ == '__main__':
    app.run(debug=os.getenv('FLASK_DEBUG') == '1', host='127.0.0.1', port=5000)
