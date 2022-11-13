package com.example.theenglishpremierleague.ui.data.remote

import retrofit2.http.Header
import retrofit2.http.Query

interface RemoteRepository {
    // suspend fun getAllMatches(@Query("date") date: String, @Header("X-Auth-Token") key: String) : Response<List<MatchEntity>>

    suspend fun getAllRemoteMatches( @Header("X-Auth-Token") key: String): String
    suspend fun getTeamsImages( @Header("X-Auth-Token") key: String): String

}