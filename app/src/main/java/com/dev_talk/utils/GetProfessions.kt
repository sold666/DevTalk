package com.dev_talk.utils

import com.dev_talk.auth.structures.Profession
import com.dev_talk.auth.structures.Tag

fun getProfessions(): List<Profession> {
    return listOf(
        Profession(
            1,
            "Backend Developer",
            listOf(
                Tag(
                    1,
                    "Spring Boot",
                    false
                ),
                Tag(
                    2,
                    "Django",
                    false
                ),
                Tag(
                    3,
                    "Flask",
                    false
                )
            ),
            false
        ),
        Profession(
            2,
            "Frontend Developer",
            listOf(
                Tag(
                    4,
                    "Angular",
                    false
                ),
                Tag(
                    5,
                    "React",
                    false
                ),
                Tag(
                    6,
                    "Vue.js",
                    false
                )
            ),
            false
        ),
        Profession(
            3,
            "Game Developer",
            listOf(
                Tag(
                    7,
                    "Unity",
                    false
                ),
                Tag(
                    8,
                    "Unreal Engine",
                    false
                ),
                Tag(
                    9,
                    "Godot",
                    false
                )
            ),
            false
        ),
        Profession(
            4,
            "Data Scientist",
            listOf(
                Tag(
                    10,
                    "Machine Learning",
                    false
                ),
                Tag(
                    11,
                    "Data Mining",
                    false
                ),
                Tag(
                    12,
                    "Data Analysis",
                    false
                )
            ),
            false
        ),
        Profession(
            5,
            "Android Developer",
            listOf(
                Tag(
                    13,
                    "Android SDK",
                    false
                ),
                Tag(
                    14,
                    "Flutter",
                    false
                ),
                Tag(
                    15,
                    "React Native",
                    false
                )
            ),
            false
        ),
        Profession(
            6,
            "UI/UX Designer",
            listOf(
                Tag(
                    16,
                    "Adobe XD",
                    false
                ),
                Tag(
                    17,
                    "Figma",
                    false
                ),
                Tag(
                    18,
                    "Sketch",
                    false
                ),
                Tag(
                    19,
                    "InVision",
                    false
                )
            ),
            false
        ),
        Profession(
            7,
            "Cybersecurity Analyst",
            listOf(
                Tag(
                    20,
                    "Penetration testing",
                    false
                ),
                Tag(
                    21,
                    "Network Security",
                    false
                ),
                Tag(
                    22,
                    "Ethical Hacking",
                    false
                ),
                Tag(
                    23,
                    "Vulnerability Assessment",
                    false
                )
            ),
            false
        ),
        Profession(
            8,
            "DevOps Engineer",
            listOf(
                Tag(
                    24,
                    "Docker",
                    false
                ),
                Tag(
                    25,
                    "Kubernetes",
                    false
                ),
                Tag(
                    26,
                    "AWS",
                    false
                ),
                Tag(
                    27,
                    "Jenkins",
                    false
                ),
                Tag(
                    28,
                    "CI/CD",
                    false
                )
            ),
            false
        ),
        Profession(
            9,
            "Blockchain Developer",
            listOf(
                Tag(
                    29,
                    "Solidity",
                    false
                ),
                Tag(
                    30,
                    "Ethereum",
                    false
                ),
                Tag(
                    31,
                    "Hyperledger Fabric",
                    false
                ),
                Tag(
                    32,
                    "Smart Contracts",
                    false
                ),
                Tag(
                    33,
                    "Decentralized Applications (dApps)",
                    false
                )
            ),
            false
        )
    )
}
