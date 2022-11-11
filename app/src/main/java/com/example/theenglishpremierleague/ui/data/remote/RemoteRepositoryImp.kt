package com.example.theenglishpremierleague.ui.data.remote

class RemoteRepositoryImp(private val api: APIService.ServerApi) : RemoteRepository {


    override suspend fun getAllMatches(date: String, key: String): String {
        return api.retrofitService.getAllMatches(date, key)
    }

    override suspend fun getTeamsImages(key: String): String {
        return api.retrofitService.getTeamsImages(key)
    }

}