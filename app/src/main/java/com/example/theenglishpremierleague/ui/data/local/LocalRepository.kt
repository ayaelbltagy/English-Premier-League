package com.example.theenglishpremierleague.ui.data.local

import androidx.lifecycle.LiveData

interface LocalRepository {

    fun addFavoriteMatches  (matches : List<MatchEntity>)
    fun getFavoriteMatches () : LiveData<List<MatchEntity>>


}