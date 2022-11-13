package com.example.theenglishpremierleague.ui.data.remote

import retrofit2.http.GET
import retrofit2.http.Header

interface ServerAPIs {
    @GET("v2/competitions/2021/matches")
    suspend fun getAllMatches(@Header("X-Auth-Token") key: String): String

}