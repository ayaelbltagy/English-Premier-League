package com.example.theenglishpremierleague.ui.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MatchDAO {

    // To mark flag as a favorite item on fav click
    @Query("UPDATE matches SET isFav=:value WHERE id = :id")
    fun updateFlag (value: Boolean , id: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllMatches  (matches :  List<Match>)

    @Query("SELECT * FROM matches WHERE date = :date")
    fun getAllMatches (date:String) : LiveData<List<Match>>

}