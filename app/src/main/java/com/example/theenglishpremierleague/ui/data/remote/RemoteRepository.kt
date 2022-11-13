package com.example.theenglishpremierleague.ui.data.remote

import retrofit2.http.Header
import retrofit2.http.Query

interface RemoteRepository {

    suspend fun getAllRemoteMatches( @Header("X-Auth-Token") key: String): String

}