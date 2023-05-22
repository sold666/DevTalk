package com.dev_talk.main.structures

import androidx.annotation.DrawableRes

data class Link(
    @DrawableRes val icon: Int,
    var url: String = "",
    var type: LinkType = LinkType.GITHUB
)
