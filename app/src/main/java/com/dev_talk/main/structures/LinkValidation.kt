package com.dev_talk.main.structures

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface LinkValidation {
    @GET("{name}")
    fun getData(@Path("name") username: String): Call<String>
}
