package com.example.theenglishpremierleague.ui.data.local

import androidx.lifecycle.LiveData

interface LocalRepository {

    fun addFavoriteMatches (matches :  Match)
    fun getFavoriteMatches () : LiveData<List<Match>>
    fun deleteFavoriteById(id: Long)
    fun updateIsFavValue(value: Boolean , id: Long)
    fun insertALLImages (pic: Images)
    fun loadAllImage(): List<Images>


}