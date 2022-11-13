package com.example.theenglishpremierleague.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.theenglishpremierleague.ui.data.local.*
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class FavoritesDaoTesting : TestCase() {

    private lateinit var favDao: FavoriteMatchesDAO
    private lateinit var db: MatchesDB

    @Before
    public  override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        //  val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, MatchesDB::class.java)
            // Allowing main thread queries, just for testing.
            .build()
        favDao = db.getFavDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }


    @Test
    @Throws(Exception::class)
    fun testInsertInFavTable() = runBlocking {
        val favMatch = Favorite(1001,"finished","2022-5-7","1","0","liver pool",
            5,"city",100,false)
       favDao.addFavoriteMatches(favMatch)
    }

    @Test
    @Throws(Exception::class)
    fun testUpdateFlag() = runBlocking {
        val match = Match(1001,"finished","2022-5-7","1","0","liver pool",
            5,"city",100,false,"2022-5-7","2022-5-7")
        val dataArrayList: ArrayList<Match> = ArrayList()
        dataArrayList.add(match)

    }

}