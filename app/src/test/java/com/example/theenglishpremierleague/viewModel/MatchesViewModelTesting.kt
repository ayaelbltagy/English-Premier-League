package com.example.theenglishpremierleague.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.theenglishpremierleague.rule.MainCoroutineRule
import com.example.theenglishpremierleague.ui.data.local.Favorite
import com.example.theenglishpremierleague.ui.presentation.MatchesViewModel
import junit.framework.TestCase
import kotlinx.coroutines.*
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.coroutines.CoroutineContext


@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class MatchesViewModelTesting : CoroutineScope {
    val job = Job()
    @get:Rule
    val taskRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutine = MainCoroutineRule()

    private lateinit var viewModel: MatchesViewModel

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Unconfined

    @Before
    fun setup() {
        viewModel = MatchesViewModel(ApplicationProvider.getApplicationContext())
    }
    @Test
     fun getMatchesTesting () = runBlocking {
        val favMatch = Favorite(1001,"finished","2022-5-7","1","0","liver pool", 5,"city",100,false)
        viewModel.saveFixtures(favMatch)
        TestCase.assertEquals(viewModel.favList.value?.size,1)
    }



}