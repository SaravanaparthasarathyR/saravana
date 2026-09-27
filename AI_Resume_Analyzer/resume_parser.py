from pypdf import PdfReader
import re


SKILLS = [
    "python",
    "java",
    "c++",
    "sql",
    "mysql",
    "mongodb",
    "pandas",
    "numpy",
    "machine learning",
    "deep learning",
    "artificial intelligence",
    "nlp",
    "tensorflow",
    "pytorch",
    "scikit-learn",
    "power bi",
    "tableau",
    "excel",
    "hadoop",
    "spark",
    "etl",
    "aws",
    "azure",
    "flask",
    "django",
    "html",
    "css",
    "javascript",
    "git",
    "github",
    "statistics"
]


def extract_text(pdf_file):

    reader = PdfReader(pdf_file)

    text = ""

    for page in reader.pages:

        page_text = page.extract_text()

        if page_text:
            text += page_text + "\n"

    return text


def clean_text(text):

    text = text.lower()

    text = re.sub(
        r"\s+",
        " ",
        text
    )

    return text.strip()


def extract_skills(text):

    skills_found = []

    for skill in SKILLS:

        pattern = r"\b" + re.escape(skill) + r"\b"

        if re.search(pattern, text):
            skills_found.append(skill)

    return skills_found