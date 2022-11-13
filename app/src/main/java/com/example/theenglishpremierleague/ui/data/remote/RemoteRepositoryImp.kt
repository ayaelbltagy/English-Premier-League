package com.example.theenglishpremierleague.ui.data.remote

class RemoteRepositoryImp(private val api: ServerAPIs) : RemoteRepository {


    override suspend fun getAllRemoteMatches(key: String): String {
        return api.getAllMatches(key)
    }


}