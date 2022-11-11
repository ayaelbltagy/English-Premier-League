package com.example.theenglishpremierleague.ui.data.remote

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ServerAPIs {
    @GET("v2/competitions/2021/matches?")
    // suspend fun getAllMatches(@Query("date") date: String,@Header("X-Auth-Token") key: String) : Response<List<MatchEntity>>
    suspend fun getAllMatches(@Query("date") date: String, @Header("X-Auth-Token") key: String): String
    @GET("v4/teams")
    suspend fun getTeamsImages( @Header("X-Auth-Token") key: String): String



}