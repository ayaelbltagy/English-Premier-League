package com.example.theenglishpremierleague.ui.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class Images (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val crest : String,
)