package com.dev_talk.utils

import com.dev_talk.common.structures.ProfessionDto
import com.dev_talk.common.structures.TagDto

fun getProfessions(): List<ProfessionDto> {
    return listOf(
        ProfessionDto(
            1,
            "Backend Developer",
            listOf(
                TagDto(
                    1,
                    "Spring Boot",
                    false
                ),
                TagDto(
                    2,
                    "Django",
                    false
                ),
                TagDto(
                    3,
                    "Flask",
                    false
                )
            ),
            false
        ),
        ProfessionDto(
            2,
            "Frontend Developer",
            listOf(
                TagDto(
                    4,
                    "Angular",
                    false
                ),
                TagDto(
                    5,
                    "React",
                    false
                ),
                TagDto(
                    6,
                    "Vue.js",
                    false
                )
            ),
            false
        ),
        ProfessionDto(
            3,
            "Game Developer",
            listOf(
                TagDto(
                    7,
                    "Unity",
                    false
                ),
                TagDto(
                    8,
                    "Unreal Engine",
                    false
                ),
                TagDto(
                    9,
                    "Godot",
                    false
                )
            ),
            false
        ),
        ProfessionDto(
            4,
            "Data Scientist",
            listOf(
                TagDto(
                    10,
                    "Machine Learning",
                    false
                ),
                TagDto(
                    11,
                    "Data Mining",
                    false
                ),
                TagDto(
                    12,
                    "Data Analysis",
                    false
                )
            ),
            false
        ),
        ProfessionDto(
            5,
            "Android Developer",
            listOf(
                TagDto(
                    13,
                    "Android SDK",
                    false
                ),
                TagDto(
                    14,
                    "Flutter",
                    false
                ),
                TagDto(
                    15,
                    "React Native",
                    false
                )
            ),
            false
        ),
        ProfessionDto(
            6,
            "UI UX Designer",
            listOf(
                TagDto(
                    16,
                    "Adobe XD",
                    false
                ),
                TagDto(
                    17,
                    "Figma",
                    false
                ),
                TagDto(
                    18,
                    "Sketch",
                    false
                ),
                TagDto(
                    19,
                    "InVision",
                    false
                )
            ),
            false
        ),
        ProfessionDto(
            7,
            "Cybersecurity Analyst",
            listOf(
                TagDto(
                    20,
                    "Penetration testing",
                    false
                ),
                TagDto(
                    21,
                    "Network Security",
                    false
                ),
                TagDto(
                    22,
                    "Cryptography",
                    false
                ),
                TagDto(
                    23,
                    "Vulnerability Assessment",
                    false
                )
            ),
            false
        ),
        ProfessionDto(
            8,
            "DevOps Engineer",
            listOf(
                TagDto(
                    24,
                    "Docker",
                    false
                ),
                TagDto(
                    25,
                    "Kubernetes",
                    false
                ),
                TagDto(
                    26,
                    "AWS",
                    false
                ),
                TagDto(
                    27,
                    "Jenkins",
                    false
                ),
                TagDto(
                    28,
                    "CI CD",
                    false
                )
            ),
            false
        ),
        ProfessionDto(
            9,
            "Blockchain Developer",
            listOf(
                TagDto(
                    29,
                    "Solidity",
                    false
                ),
                TagDto(
                    30,
                    "Networks",
                    false
                ),
                TagDto(
                    31,
                    "Hyperledger Fabric",
                    false
                ),
                TagDto(
                    32,
                    "Smart Contracts",
                    false
                ),
                TagDto(
                    33,
                    "Decentralized Applications (dApps)",
                    false
                )
            ),
            false
        )
    )
}
