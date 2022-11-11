package com.example.theenglishpremierleague.ui.data.local

import androidx.lifecycle.LiveData

class LocalRepositoryImp (private val database :MatchesDB): LocalRepository {
    override  fun addFavoriteMatches(matches: Match) {
        database.getMatchesFromDao().addFavoriteMatches(matches)
    }

    override fun getFavoriteMatches(): LiveData<List<Match>> {
       return database.getMatchesFromDao().getFavoriteMatches()
    }

    override fun deleteFavoriteById(id: Long) {
        database.getMatchesFromDao().deleteFavoriteById(id)
    }

    override fun updateIsFavValue(value: Boolean, id: Long) {
        database.getMatchesFromDao().updateIsFavValue(value,id)

    }

    override fun insertALLImages(pic: Images) {
        database.getImagesFromDao().insertALLImages(pic)
    }

    override fun loadAllImage(): List<Images> {
        return database.getImagesFromDao().loadAllImage()
     }


}