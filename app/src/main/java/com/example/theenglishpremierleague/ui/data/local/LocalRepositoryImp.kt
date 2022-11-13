package com.example.theenglishpremierleague.ui.data.local

import androidx.lifecycle.LiveData

class LocalRepositoryImp (private val database :MatchesDB): LocalRepository {


    override fun insertALLImages(pic: Images) {
        database.getImagesFromDao().insertALLImages(pic)
    }

    override fun loadAllImage(): List<Images> {
        return database.getImagesFromDao().loadAllImage()
     }

    override fun addFavoriteMatches(match: Favorite) {
        database.getFavDao().addFavoriteMatches(match)
    }

    override fun getFavoriteMatches(): LiveData<List<Favorite>> {
       return database.getFavDao().getFavoriteMatches()
    }

    override fun deleteFavoriteById(id: Long) {
        database.getFavDao().deleteFavoriteById(id)
    }

    override fun updateFlag(value: Boolean, id: Long) {
        database.getMatchesFromDao().updateFlag(value,id)
    }

    override fun addAllMatches(matches: List<Match>) {
        database.getMatchesFromDao().addAllMatches(matches)
    }
    override fun getAllMatches(date:String): LiveData<List<Match>> {
        return  database.getMatchesFromDao().getAllMatches(date)
    }


}