package com.example.theenglishpremierleague.ui.data.local

import androidx.lifecycle.LiveData


interface LocalRepository {

    // Favorite
     fun addFavoriteMatches  (match :  Favorite)
     fun getFavoriteMatches () : LiveData<List<Favorite>>
     fun deleteFavoriteById(id: Long)

    // All matches
    fun updateFlag (value: Boolean , id: Long)
    fun addAllMatches  (matches :  List<Match>)
    fun getAllMatches (date:String) : LiveData<List<Match>>




}