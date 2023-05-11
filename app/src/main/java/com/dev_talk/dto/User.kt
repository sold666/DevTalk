package com.dev_talk.dto

import android.os.Parcelable
import com.dev_talk.common.structures.ProfessionDto
import com.dev_talk.common.structures.TagDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    var name: String,
    var surname: String,
    var email: String,
    var password: String,
    var professions: List<ProfessionDto>,
    var tags: List<TagDto>
) : Parcelable {

    constructor() : this(
        "", "",
        "",
        "", arrayListOf(),
        arrayListOf()
    ) {

    }


}
