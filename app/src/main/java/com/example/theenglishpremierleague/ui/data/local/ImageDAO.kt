package com.example.theenglishpremierleague.ui.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ImageDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertALLImages (pic: Images)

    @Query("SELECT * FROM images")
    fun loadAllImage(): List<Images>
}