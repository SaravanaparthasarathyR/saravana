import streamlit as st

from resume_parser import (
    extract_text,
    clean_text,
    extract_skills
)

from job_recommender import recommend_jobs


# ---------------------------------
# Page Configuration
# ---------------------------------

st.set_page_config(
    page_title="AI Resume Analyzer",
    page_icon="📄",
    layout="wide"
)


# ---------------------------------
# Heading
# ---------------------------------

st.title(
    "📄 AI Resume Analyzer & Job Recommendation"
)

st.write(
    "Upload your resume to analyze your technical "
    "skills and discover suitable job roles."
)


st.divider()


# ---------------------------------
# User Information
# ---------------------------------

st.sidebar.title(
    "👤 Candidate Details"
)

name = st.sidebar.text_input(
    "Enter your name"
)

email = st.sidebar.text_input(
    "Enter your email"
)


# ---------------------------------
# Resume Upload
# ---------------------------------

st.header(
    "📤 Upload Resume"
)

resume = st.file_uploader(
    "Choose your resume",
    type=["pdf"]
)


# ---------------------------------
# Analyze Button
# ---------------------------------

if resume is not None:

    if st.button(
        "🔍 Analyze Resume",
        type="primary"
    ):

        if name.strip() == "":

            st.warning(
                "Please enter your name."
            )

            st.stop()


        # ---------------------------------
        # Resume Processing
        # ---------------------------------

        resume_text = extract_text(
            resume
        )

        cleaned_text = clean_text(
            resume_text
        )

        skills = extract_skills(
            cleaned_text
        )


        # ---------------------------------
        # Basic Resume Information
        # ---------------------------------

        st.success(
            "Resume analyzed successfully!"
        )

        st.header(
            "📊 Resume Analysis"
        )


        col1, col2, col3 = st.columns(3)


        with col1:

            st.metric(
                "Skills Found",
                len(skills)
            )


        with col2:

            st.metric(
                "Resume Words",
                len(resume_text.split())
            )


        with col3:

            if len(skills) >= 10:
                level = "Strong"

            elif len(skills) >= 5:
                level = "Good"

            else:
                level = "Basic"

            st.metric(
                "Technical Profile",
                level
            )


        # ---------------------------------
        # Skills
        # ---------------------------------

        st.header(
            "🛠️ Skills Detected"
        )


        if skills:

            columns = st.columns(3)

            for index, skill in enumerate(skills):

                with columns[index % 3]:

                    st.success(
                        "✓ " + skill.title()
                    )

        else:

            st.warning(
                "No matching technical skills were found."
            )


        # ---------------------------------
        # Job Recommendations
        # ---------------------------------

        st.header(
            "🎯 Recommended Job Roles"
        )


        try:

            recommendations = recommend_jobs(
                cleaned_text
            )


            for _, job in recommendations.iterrows():

                job_name = job["job_title"]

                score = round(
                    job["match_score"],
                    1
                )


                with st.container():

                    st.subheader(
                        job_name
                    )

                    st.write(
                        f"Job Match: **{score}%**"
                    )

                    progress_value = min(
                        score / 100,
                        1.0
                    )

                    st.progress(
                        progress_value
                    )

                    st.write(
                        "**Skills required:**"
                    )

                    st.write(
                        job["skills"]
                    )

                    st.divider()


        except Exception as error:

            st.error(
                "Unable to generate job recommendations."
            )

            st.write(
                error
            )


        # ---------------------------------
        # Extracted Resume
        # ---------------------------------

        with st.expander(
            "📄 View Extracted Resume Text"
        ):

            st.write(
                resume_text
            )