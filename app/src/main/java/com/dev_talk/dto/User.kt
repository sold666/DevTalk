package com.dev_talk.dto

import com.dev_talk.auth.structures.Profession
import com.dev_talk.auth.structures.Tag

class User(
    private var name: String,
    private var surname: String,
    private var email: String,
    private var password: String,
    private var professions: List<Profession>,
    private var tags: List<Tag>
)
