package com.example.theenglishpremierleague.ui.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MatchDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavoriteMatches  (matches :  Match)

    @Query("SELECT * FROM matches")
    fun getFavoriteMatches () : LiveData<List<Match>>

    @Query("DELETE FROM matches WHERE id = :id")
    fun deleteFavoriteById(id: Long)

    @Query("UPDATE matches SET isFav=:value WHERE id = :id")
    fun updateIsFavValue(value: Boolean , id: Long)
}