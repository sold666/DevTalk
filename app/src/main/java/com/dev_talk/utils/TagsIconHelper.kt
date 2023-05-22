package com.dev_talk.utils

import com.dev_talk.R

fun getTagsIcon(tag: String): Int {
    return when (tag) {
        "Spring Boot" -> R.drawable.spring_boot
        "Django" -> R.drawable.django
        "Flask" -> R.drawable.flask
        "Angular" -> R.drawable.angular
        "React" -> R.drawable.react
        "Vue.js" -> R.drawable.vuejs
        "Unity" -> R.drawable.unity
        "Unreal Engine" -> R.drawable.unreal_engine
        "Godot" -> R.drawable.godot
        "Machine Learning" -> R.drawable.machine_learning
        "Data Mining" -> R.drawable.data_mining
        "Data Analysis" -> R.drawable.data_analysis
        "Android SDK" -> R.drawable.android_sdk
        "Flutter" -> R.drawable.flutter
        "React Native" -> R.drawable.react
        "Adobe XD" -> R.drawable.adobe_xd
        "Figma" -> R.drawable.figma
        "Sketch" -> R.drawable.sketch
        "InVision" -> R.drawable.invision
        "Penetration testing" -> R.drawable.penetration_testing
        "Network Security" -> R.drawable.network_security
        "Cryptography" -> R.drawable.cryptography
        "Vulnerability Assessment" -> R.drawable.vulnerability_assessment
        "Docker" -> R.drawable.docker
        "Kubernetes" -> R.drawable.kubernetes
        "AWS" -> R.drawable.aws
        "Jenkins" -> R.drawable.jenkins
        "CI CD" -> R.drawable.ci_cd
        "Solidity" -> R.drawable.solidity
        "Networks" -> R.drawable.networks
        "Hyperledger Fabric" -> R.drawable.hyperledger_fabric
        "Smart Contracts" -> R.drawable.smart_contracts
        "Decentralized Applications (dApps)" -> R.drawable.dapps
        else -> R.drawable.default_avatar_tag
    }
}
