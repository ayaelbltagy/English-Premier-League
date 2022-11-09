package com.example.theenglishpremierleague.ui.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [MatchEntity::class],version =2,exportSchema = false)
abstract class MatchesDB : RoomDatabase() {

    // to access database you should get from DAO
    abstract fun getMatchesFromDao (): MatchDAO

    // to prevent more connection should use one instance from database so make single tone design pattern
    companion object {

        @Volatile
        private var INSTANCE: MatchesDB? = null

        // function to get object from database class
        fun getInstance(context: Context): MatchesDB {

            // to block code until this instance created
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MatchesDB::class.java,"MATCHES_DATA_BASE"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}

