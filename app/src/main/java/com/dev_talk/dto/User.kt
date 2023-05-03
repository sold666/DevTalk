package com.dev_talk.dto

import com.dev_talk.auth.structures.Profession
import com.dev_talk.auth.structures.Tag

class User(
    var name: String,
    var surname: String,
    var email: String,
    var password: String,
    var professions: List<Profession>,
    var tags: List<Tag>
)
