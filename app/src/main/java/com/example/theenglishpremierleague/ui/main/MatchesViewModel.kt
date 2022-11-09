package com.example.theenglishpremierleague.ui.main

import android.app.Application
import androidx.lifecycle.*
 import com.example.theenglishpremierleague.ui.data.local.MatchEntity
import com.example.theenglishpremierleague.ui.data.local.MatchesDB.Companion.getInstance
import com.example.theenglishpremierleague.ui.data.remote.APIService
import com.example.theenglishpremierleague.ui.data.remote.APIService.API_KEY
import com.example.theenglishpremierleague.ui.data.remote.RemoteRepositoryImp
import com.google.gson.Gson
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MatchesViewModel(application: Application) : AndroidViewModel(application) {



    private val database = getInstance(application)


    // prepare remote data
    private var remoteRepositoryImp: RemoteRepositoryImp

    private var _remoteLiveData = MutableLiveData<List<MatchEntity>>()

    val remoteList: LiveData<List<MatchEntity>> get() = _remoteLiveData

    init {
        remoteRepositoryImp = RemoteRepositoryImp(APIService.ServerApi)

        getListOfRefreshedMatches()
    }

    private fun getListOfRefreshedMatches() {
        viewModelScope.launch {
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
                    val homeTeamScore = matchJson.getJSONObject("score").getJSONObject("fullTime").get("homeTeam")
                    val awayTeamScore = matchJson.getJSONObject("score").getJSONObject("fullTime").get("awayTeam")
                    val homeTeamName = matchJson.getJSONObject("homeTeam").getString("name")
                    val homeTeamId = matchJson.getJSONObject("homeTeam").getLong("id")
                    val awayTeamName = matchJson.getJSONObject("awayTeam").getString("name")
                    val awayTeamId = matchJson.getJSONObject("awayTeam").getLong("id")

                    val model = MatchEntity(id, status, utcDate, homeTeamScore.toString(), awayTeamScore.toString(), homeTeamName, homeTeamId,
                        awayTeamName, awayTeamId)
                    dataArrayList.add(model)
                }
                _remoteLiveData.postValue(dataArrayList.toList())

            }
        }
    }




}