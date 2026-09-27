# Campus Maintenance Intelligence

A final year project prototype: issue submission, Firestore storage, rule-based category assignment, TF-IDF similarity for possible duplicate reports, status tracking, and dashboard analytics. Python backend and HTML/CSS/JavaScript frontend.

## Requirements
- Python 3.10 or newer
- A Firebase project with Cloud Firestore enabled
- Internet connection when running

## Firebase setup
1. Go to https://console.firebase.google.com/ and create a project.
2. Open **Build > Firestore Database > Create database**. Choose a nearby region and production mode. The backend uses admin credentials; it does not need public Firestore rules.
3. Open **Project settings > Service accounts > Generate new private key**.
4. Save the downloaded JSON inside this folder as `serviceAccountKey.json`. Keep it private and never upload it to GitHub or submit it with your project report.

## Run in VS Code (Windows PowerShell)
Open a terminal in this folder:

```powershell
py -m venv .venv
.venv\Scripts\Activate.ps1
python -m pip install -r requirements.txt
python app.py
```

Open http://127.0.0.1:5000 in your browser. If activation is blocked, run `.venv\Scripts\python.exe -m pip install -r requirements.txt` and `.venv\Scripts\python.exe app.py` instead.

## Demo sequence
1. Submit `Projector not working`, location `Block A Lab 2`, with a description of 10+ characters.
2. Submit a similar report in the same location; the app shows a possible duplicate.
3. Change one report to In Progress and another to Resolved.
4. See status, category, and location counts update.

## Academic explanation
The category assignment uses transparent keyword rules. Duplicate detection uses TF-IDF vectors and cosine similarity, limited to unresolved issues at the same location. A similarity score of 0.45 or higher suggests a possible duplicate; it does not automatically delete reports. Evaluate on your own labelled examples with precision, recall, and F1 score. There is no trained classifier or verified accuracy claim.

## Limitations and next steps
This version is a local demonstration: anyone with access to the running page can change statuses. Before public deployment, add authentication and staff roles, input rate limiting, and stronger validation. Keep the Firebase admin key only on the server.
