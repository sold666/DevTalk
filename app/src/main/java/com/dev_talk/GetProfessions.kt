package com.dev_talk

import com.dev_talk.structures.Profession

fun getProfessions(): ArrayList<Profession> {
    return arrayListOf(
        Profession(
            1,
            "Backend Developer",
            arrayListOf(
                "Spring Boot",
                "Django",
                "Flask"
            )
        ),
        Profession(
            2,
            "Frontend Developer",
            arrayListOf(
                "Angular",
                "React",
                "Vue.js"
            )
        ),
        Profession(
            3,
            "Game Developer",
            arrayListOf(
                "Unity",
                "Unreal Endine",
                "Godot"
            )
        ),
        Profession(
            4,
            "Data Scientist",
            arrayListOf(
                "Machine Learning",
                "Data Mining",
                "Data Analysis"
            )
        ),
        Profession(
            5,
            "Android Developer",
            arrayListOf(
                "Android SDK",
                "Flutter",
                "React Native"
            )
        ),
        Profession(
            6,
            "UI/UX Designer",
            arrayListOf(
                "Adobe XD",
                "Figma",
                "Sketch",
                "InVision"
            )
        ),
        Profession(
            7,
            "Cybersecurity Analyst",
            arrayListOf(
                "Penetration testing",
                "Network Security",
                "Ethical Hacking",
                "Vulnerability Assessment"
            )
        ),
        Profession(
            8,
            "DevOps Engineer",
            arrayListOf(
                "Docker",
                "Kubernetes",
                "AWS",
                "Jenkins",
                "CI/CD"
            )
        ),
        Profession(
            9,
            "Blockchain Developer",
            arrayListOf(
                "Solidity",
                "Ethereum",
                "Hyperledger Fabric",
                "Smart Contracts",
                "Decentralized Applications (dApps)"
            )
        )
    )
}
