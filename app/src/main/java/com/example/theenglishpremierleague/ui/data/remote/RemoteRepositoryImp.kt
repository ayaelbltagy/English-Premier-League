package com.example.theenglishpremierleague.ui.data.remote

class RemoteRepositoryImp(private val api: APIService.ServerApi) : RemoteRepository {


    override suspend fun getAllRemoteMatches(key: String): String {
        return api.retrofitService.getAllMatches(key)
    }

    override suspend fun getTeamsImages(key: String): String {
        return api.retrofitService.getTeamsImages(key)
    }

}