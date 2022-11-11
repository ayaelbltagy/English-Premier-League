package com.example.theenglishpremierleague.ui.data.local

import androidx.lifecycle.LiveData

class LocalRepositoryImp (private val database :MatchesDB): LocalRepository {
    override  fun addFavoriteMatches(matches: MatchEntity) {
        database.getMatchesFromDao().addFavoriteMatches(matches)
    }

    override fun getFavoriteMatches(): LiveData<List<MatchEntity>> {
       return database.getMatchesFromDao().getFavoriteMatches()
    }

    override fun deleteFavoriteById(id: Long) {
        database.getMatchesFromDao().deleteFavoriteById(id)
    }

    override fun updateIsFavValue(value: Boolean, id: Long) {
        database.getMatchesFromDao().updateIsFavValue(value,id)

    }
}