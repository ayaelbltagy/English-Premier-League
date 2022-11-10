package com.example.theenglishpremierleague.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.theenglishpremierleague.ui.data.local.LocalRepositoryImp
import com.example.theenglishpremierleague.ui.data.local.MatchEntity
import com.example.theenglishpremierleague.ui.data.local.MatchesDB.Companion.getInstance
import com.example.theenglishpremierleague.ui.data.remote.APIService
import com.example.theenglishpremierleague.ui.data.remote.APIService.API_KEY
import com.example.theenglishpremierleague.ui.data.remote.RemoteRepositoryImp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.SocketTimeoutException

class MatchesViewModel(application: Application) : AndroidViewModel(application) {

    // prepare local database
    private val database = getInstance(application)
    private var localRepositoryImp: LocalRepositoryImp
    private var emptyList = mutableListOf<MatchEntity>() // empty list will fill it after fav click
    private var _localLiveData = MutableLiveData<List<MatchEntity>>() // to add fav item
    val localList: MediatorLiveData<List<MatchEntity>> = MediatorLiveData() // used for showing whatever on it
    private var list: LiveData<List<MatchEntity>>

    // prepare remote data
    private var remoteRepositoryImp: RemoteRepositoryImp
    private var _remoteLiveData = MutableLiveData<List<MatchEntity>>()
    val remoteList: LiveData<List<MatchEntity>> get() = _remoteLiveData

    init {
        remoteRepositoryImp = RemoteRepositoryImp(APIService.ServerApi)
        localRepositoryImp = LocalRepositoryImp(database)
        list = localRepositoryImp.getFavoriteMatches()

        getListOfRefreshedMatches()
        getListOfLocalMatches()
    }

    private fun getListOfRefreshedMatches() {
        viewModelScope.launch {
            try {
                val response = remoteRepositoryImp.getAllMatches("TODAY", API_KEY)
                if (response != null) {
                    val responseJsonObject = JSONObject(response)
                    val dataArrayList: ArrayList<MatchEntity> = ArrayList()
                    val jsonArray = responseJsonObject.getJSONArray("matches")
                    for (i in 0 until jsonArray.length()) {
                        //val model: MatchEntity = Gson().fromJson(jsonArray.get(i).toString(), MatchEntity::class.java)
                        val matchJson = jsonArray.getJSONObject(i)
                        val id = matchJson.getLong("id")
                        val status = matchJson.getString("status")
                        val utcDate = matchJson.getString("utcDate")
                        val homeTeamScore =
                            matchJson.getJSONObject("score").getJSONObject("fullTime").get("homeTeam")
                        val awayTeamScore =
                            matchJson.getJSONObject("score").getJSONObject("fullTime").get("awayTeam")
                        val homeTeamName = matchJson.getJSONObject("homeTeam").getString("name")
                        val homeTeamId = matchJson.getJSONObject("homeTeam").getLong("id")
                        val awayTeamName = matchJson.getJSONObject("awayTeam").getString("name")
                        val awayTeamId = matchJson.getJSONObject("awayTeam").getLong("id")

                        val model = MatchEntity(
                            id,
                            status,
                            utcDate,
                            homeTeamScore.toString(),
                            awayTeamScore.toString(),
                            homeTeamName,
                            homeTeamId,
                            awayTeamName,
                            awayTeamId
                        )
                        dataArrayList.add(model)
                    }
                    _remoteLiveData.postValue(dataArrayList.toList())

                }
            }
            catch (ex: SocketTimeoutException){

            }
        }
    }

    fun getListOfLocalMatches() {
        localList.addSource(list) {
            localList.value = it
        }
    }


    suspend fun saveFixtures(oneMatch: MatchEntity) {
        withContext(Dispatchers.IO) {
            localRepositoryImp.addFavoriteMatches(oneMatch)
        }
        emptyList.add(oneMatch)
        _localLiveData.value = emptyList
        localList.value = _localLiveData.value
        Log.i("listSize", (_localLiveData.value as MutableList<MatchEntity>).size.toString())
        Log.i("testSize", localList.value?.size.toString())
     }

    suspend fun removeSourceFromFav (oneMatch: MatchEntity) {

        withContext(Dispatchers.IO) {
           localRepositoryImp.deleteFavoriteById(oneMatch.id)
            Log.i("", localRepositoryImp.getFavoriteMatches().value?.size.toString())
       }
        list = localRepositoryImp.getFavoriteMatches()
        localList.addSource(list){
            localList.value = it
        }

     }

    private val _index = MutableLiveData<Int>()
    val text: LiveData<String> = Transformations.map(_index) {
        "$it"
    }

    fun setIndex(index: Int) {
        _index.value = index
    }


}