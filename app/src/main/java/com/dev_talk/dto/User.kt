package com.dev_talk.dto

import com.dev_talk.common.structures.ProfessionDto
import com.dev_talk.common.structures.TagDto

data class User(
    var name: String,
    var surname: String,
    var email: String,
    var password: String,
    var professions: List<ProfessionDto>,
    var tags: List<TagDto>
) {

    constructor() : this(
        "", "",
        "",
        "", arrayListOf(),
        arrayListOf()
    ) {

    }


}
