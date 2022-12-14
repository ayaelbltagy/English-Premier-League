package com.example.theenglishpremierleague.ui.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorites")
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val status: String,
    val date: String,
    val homeTeamScore: String,
    val awayTeamScore: String,
    val homeTeamName: String,
    val homeTeamId: Long,
    val awayTeamName: String,
    val awayTeamId: Long,
    val isFav: Boolean
)