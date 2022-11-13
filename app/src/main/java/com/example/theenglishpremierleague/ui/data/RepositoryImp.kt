package com.example.theenglishpremierleague.ui.data

import androidx.lifecycle.LiveData
import com.example.theenglishpremierleague.ui.data.local.Favorite
import com.example.theenglishpremierleague.ui.data.local.LocalRepository
import com.example.theenglishpremierleague.ui.data.local.Match
import com.example.theenglishpremierleague.ui.data.remote.RemoteRepository

class RepositoryImp(private val local:LocalRepository,private val remote:RemoteRepository) :Repository {

    override fun addFavoriteMatches(match: Favorite) = local.addFavoriteMatches(match)

    override fun getFavoriteMatches(): LiveData<List<Favorite>> = local.getFavoriteMatches()

    override fun deleteFavoriteById(id: Long) = local.deleteFavoriteById(id)

    override fun updateFlag(value: Boolean, id: Long) = local.updateFlag(value,id)

    override fun addAllMatches(matches: List<Match>) = local.addAllMatches(matches)

    override fun getAllMatches(date: String): LiveData<List<Match>> = local.getAllMatches(date)

    override suspend fun getAllRemoteMatches(key: String): String = remote.getAllRemoteMatches(key)


}