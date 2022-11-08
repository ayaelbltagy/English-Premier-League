package com.example.theenglishpremierleague.ui.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "match_table")
data class MatchEntity (
     @PrimaryKey(autoGenerate = true)
     val id: Long,
//     val homeTeam: HomeTeam,
//     val awayTeam: AwayTeam,
//     val score : Score,
     val status : String
      )
//
//@Entity(tableName = "home_team_table")
//data class HomeTeam(
//     @PrimaryKey(autoGenerate = true)
//     val id: Int,
//     val name: String
//)
//
//@Entity(tableName = "away_team_table")
//data class AwayTeam(
//     @PrimaryKey(autoGenerate = true)
//     val id: Int,
//     val name: String
//)
//
//@Entity(tableName = "score_table")
//data class Score(
//     val duration: String,
//     val extraTime: ExtraTime,
//     val fullTime: FullTime,
//     val halfTime: HalfTime,
//     val penalties: Penalties,
//     val winner: String
//) {
//     data class ExtraTime(
//          val awayTeam: Any,
//          val homeTeam: Any
//     )
//
//     data class FullTime(
//          val awayTeam: Int,
//          val homeTeam: Int
//     )
//
//     data class HalfTime(
//          val awayTeam: Int,
//          val homeTeam: Int
//     )
//
//     data class Penalties(
//          val awayTeam: Any,
//          val homeTeam: Any
//     )

