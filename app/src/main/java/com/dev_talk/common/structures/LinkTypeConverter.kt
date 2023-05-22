package com.dev_talk.common.structures

import com.dev_talk.R
import com.dev_talk.main.structures.LinkType

class LinkTypeConverter {
    companion object {
        val links: Map<String, Int> = mapOf(
            "Github" to R.drawable.ic_github,
            "Gitlab" to R.drawable.ic_gitlab,
            "Linkedin" to R.drawable.ic_linked
        )
        val types: Map<String, LinkType> = mapOf(
            "Github" to LinkType.GITHUB,
            "Gitlab" to LinkType.GITLAB,
            "Linkedin" to LinkType.LINKEDIN
        )
        val strings: Map<LinkType, String> = mapOf(
            LinkType.GITHUB to "Github",
            LinkType.GITLAB to "Gitlab",
            LinkType.LINKEDIN to "Linkedin"
        )
    }
}