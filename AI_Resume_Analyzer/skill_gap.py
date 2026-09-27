def find_skill_gap(user_skills, required_skills):

    user_skills = [
        skill.lower().strip()
        for skill in user_skills
    ]

    required_skills = [
        skill.lower().strip()
        for skill in required_skills.split()
    ]

    missing_skills = []

    for skill in required_skills:

        if skill not in user_skills:
            missing_skills.append(skill)

    return missing_skills