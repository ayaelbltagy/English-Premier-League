package com.example.theenglishpremierleague.ui.data.local

import androidx.lifecycle.LiveData

interface LocalRepository {

    fun addFavoriteMatches (matches :  MatchEntity)
    fun getFavoriteMatches () : LiveData<List<MatchEntity>>
    fun deleteFavoriteById(id: Long)



}