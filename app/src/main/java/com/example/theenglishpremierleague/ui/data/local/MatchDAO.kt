package com.example.theenglishpremierleague.ui.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MatchDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavoriteMatches  (matches : List<MatchEntity>)

    @Query("SELECT * FROM match_table")
    fun getFavoriteMatches () : LiveData<List<MatchEntity>>
}