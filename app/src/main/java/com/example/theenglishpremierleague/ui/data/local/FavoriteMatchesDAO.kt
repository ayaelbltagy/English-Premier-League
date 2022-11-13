package com.example.theenglishpremierleague.ui.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteMatchesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavoriteMatches(match: Favorite)

    @Query("SELECT * FROM favorites")
    fun getFavoriteMatches(): LiveData<List<Favorite>>

    @Query("DELETE FROM favorites WHERE id = :id")
    fun deleteFavoriteById(id: Long)

}