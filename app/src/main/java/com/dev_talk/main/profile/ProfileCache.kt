package com.dev_talk.main.profile

import android.net.Uri
import com.dev_talk.main.structures.Link
import com.dev_talk.main.structures.ProfileData


object ProfileCache {
    var avatar: Uri? = null
    var name: String = ""
    var links: ArrayList<Link> = arrayListOf()
    var profileData: ArrayList<ProfileData> = arrayListOf()
}
