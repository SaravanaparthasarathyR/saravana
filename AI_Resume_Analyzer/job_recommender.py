import pandas as pd

from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity


def recommend_jobs(resume_text):

    jobs = pd.read_csv("jobs.csv")

    jobs["skills"] = jobs["skills"].fillna("")

    documents = [resume_text] + jobs["skills"].tolist()

    vectorizer = TfidfVectorizer(
        stop_words="english"
    )

    vectors = vectorizer.fit_transform(documents)

    similarity = cosine_similarity(
        vectors[0:1],
        vectors[1:]
    )[0]

    jobs["match_score"] = similarity * 100

    jobs = jobs.sort_values(
        by="match_score",
        ascending=False
    )

    return jobs.head(5)