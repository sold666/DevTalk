package com.dev_talk

import com.dev_talk.structures.Profession

fun getProfessions(): ArrayList<Profession> {
    return arrayListOf(
        Profession(
            "Backend-Developer",
            arrayListOf("Spring Boot", "Django", "Flask")
        ),
        Profession(
            "Frontend-Developer",
            arrayListOf("Angular", "React", "Vue.js")
        ),
        Profession(
            "Gamedev",
            arrayListOf("Unity", "Unreal Endine", "Godot")
        ),
        Profession(
            "Data Scientist",
            arrayListOf("Neural Networks", "Big Data")
        ),
        Profession(
            "Android-Developer",
            arrayListOf("Flutter", "Dart", "Kotlin")
        )
    )
}
