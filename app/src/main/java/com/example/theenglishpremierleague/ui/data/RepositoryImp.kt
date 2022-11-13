package com.example.theenglishpremierleague.ui.data

import androidx.lifecycle.LiveData
import com.example.theenglishpremierleague.ui.data.local.Favorite
import com.example.theenglishpremierleague.ui.data.local.Images
import com.example.theenglishpremierleague.ui.data.local.LocalRepository
import com.example.theenglishpremierleague.ui.data.local.Match
import com.example.theenglishpremierleague.ui.data.remote.RemoteRepository

class RepositoryImp(private val local:LocalRepository,private val remote:RemoteRepository) :Repository {

    override fun addFavoriteMatches(match: Favorite) = local.addFavoriteMatches(match)

    override fun getFavoriteMatches(): LiveData<List<Favorite>> = local.getFavoriteMatches()

    override fun deleteFavoriteById(id: Long) = local.deleteFavoriteById(id)

    override fun updateFlag(value: Boolean, id: Long) = local.updateFlag(value,id)

    override fun addAllMatches(matches: List<Match>) {
        TODO("Not yet implemented")
    }

    override fun getAllMatches(date: String): LiveData<List<Match>> {
        TODO("Not yet implemented")
    }

    override fun insertALLImages(pic: Images) {
        TODO("Not yet implemented")
    }

    override fun loadAllImage(): List<Images> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllRemoteMatches(key: String): String {
        TODO("Not yet implemented")
    }

    override suspend fun getTeamsImages(key: String): String {
        TODO("Not yet implemented")
    }
}