package com.example.theenglishpremierleague.ui.main

import android.app.Application
import androidx.lifecycle.*
import com.example.theenglishpremierleague.ui.data.local.MatchEntity
import com.example.theenglishpremierleague.ui.data.local.MatchesDB.Companion.getInstance
import com.example.theenglishpremierleague.ui.data.remote.APIService
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
            val response = remoteRepositoryImp.getAllMatches("TODAY", "eeaf1766a5b74afa8ad221196853c1be")
            if (response != null) {
                val responseJsonObject = JSONObject(response)
                val dataArrayList: ArrayList<MatchEntity> = ArrayList()
                val jsonArray = responseJsonObject.getJSONArray("matches")
                for (i in 0 until jsonArray.length()) {
                    val model: MatchEntity = Gson().fromJson(jsonArray.get(i).toString(), MatchEntity::class.java)
                    dataArrayList.add(model)
                }
                _remoteLiveData.postValue(dataArrayList.toList())

            }
        }
    }




}