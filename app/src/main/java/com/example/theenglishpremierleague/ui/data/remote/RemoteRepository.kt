package com.example.theenglishpremierleague.ui.data.remote

import retrofit2.http.Header

interface RemoteRepository {

    suspend fun getAllRemoteMatches(@Header("X-Auth-Token") key: String): String

}