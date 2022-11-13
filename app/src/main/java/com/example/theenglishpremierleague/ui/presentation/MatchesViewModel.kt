package com.example.theenglishpremierleague.ui.presentation

 import android.app.Application
 import android.util.Log
 import androidx.lifecycle.*
import com.example.theenglishpremierleague.ui.data.local.Favorite
import com.example.theenglishpremierleague.ui.data.local.Images
import com.example.theenglishpremierleague.ui.data.local.LocalRepositoryImp
import com.example.theenglishpremierleague.ui.data.local.Match
import com.example.theenglishpremierleague.ui.data.local.MatchesDB.Companion.getInstance
import com.example.theenglishpremierleague.ui.data.remote.APIService
import com.example.theenglishpremierleague.ui.data.remote.APIService.API_KEY
import com.example.theenglishpremierleague.ui.data.remote.RemoteRepositoryImp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

import androidx.lifecycle.MutableLiveData




class MatchesViewModel(application: Application) : AndroidViewModel(application) {
      var date = MutableLiveData<String> ()
    val selectedDay: LiveData<String> get() = date
    // prepare local database
    private val database = getInstance(application)
    private var localRepositoryImp: LocalRepositoryImp
    val favList: MediatorLiveData<List<Favorite>> = MediatorLiveData()
    private var _favList: LiveData<List<Favorite>>
    private var listOfImages = listOf<Images>()
    private var imagesList = listOf<Images>()
    private lateinit var _allMatchesList: LiveData<List<Match>>
    val allMatchesList: MediatorLiveData<List<Match>> = MediatorLiveData()

    // prepare remote data
    private var remoteRepositoryImp: RemoteRepositoryImp



    init {
        remoteRepositoryImp = RemoteRepositoryImp(APIService.ServerApi)
        localRepositoryImp = LocalRepositoryImp(database)
        _favList = localRepositoryImp.getFavoriteMatches()
        getListOfRefreshedMatches()
        getListOfLocalFavMatches()
        getImagesFromdb()
     }


     fun getMatchesByFilter(date: String) {
        _allMatchesList = localRepositoryImp.getAllMatches(date)
         Log.i("testDate",_allMatchesList.value?.size.toString())
        allMatchesList.addSource(_allMatchesList) {
            allMatchesList.value = it
        }
    }

    suspend fun getImagesfromdb(): List<Images> {
        withContext(Dispatchers.IO) {
            listOfImages = localRepositoryImp.loadAllImage()
        }
        imagesList = listOfImages
        return imagesList
    }

    private fun getImagesFromdb() {
        viewModelScope.launch {
            getImagesfromdb()
        }
    }


    suspend fun saveImages(image: Images) {
        withContext(Dispatchers.IO) {
            localRepositoryImp.insertALLImages(image)
        }
    }

    fun getListOfRefreshedMatches() {
        viewModelScope.launch {
            try {
                val response = remoteRepositoryImp.getAllMatches(API_KEY)
                if (response != null) {
                    val responseJsonObject = JSONObject(response)
                    val dataArrayList: ArrayList<Match> = ArrayList()
                    val jsonArray = responseJsonObject.getJSONArray("matches")
                    for (i in 0 until jsonArray.length()) {
                         val matchJson = jsonArray.getJSONObject(i)
                        val id = matchJson.getLong("id")
                        val status = matchJson.getString("status")
                        val utcDate = matchJson.getString("utcDate")
                        val homeTeamScore =
                            matchJson.getJSONObject("score").getJSONObject("fullTime")
                                .get("homeTeam")
                        val awayTeamScore =
                            matchJson.getJSONObject("score").getJSONObject("fullTime")
                                .get("awayTeam")
                        val homeTeamName = matchJson.getJSONObject("homeTeam").getString("name")
                        val homeTeamId = matchJson.getJSONObject("homeTeam").getLong("id")
                        val awayTeamName = matchJson.getJSONObject("awayTeam").getString("name")
                        val awayTeamId = matchJson.getJSONObject("awayTeam").getLong("id")
                        val startDate = matchJson.getJSONObject("season").getString("startDate")
                        val endDate = matchJson.getJSONObject("season").getString("endDate")

                        val model = Match(
                            id,
                            status,
                            utcDate,
                            homeTeamScore.toString(),
                            awayTeamScore.toString(),
                            homeTeamName,
                            homeTeamId,
                            awayTeamName,
                            awayTeamId,
                            false,
                            startDate,
                            endDate
                        )
                        dataArrayList.add(model)
                    }
                    if(dataArrayList.size>0){
                        // to filter by next day that has matches
                        for (i in 0..dataArrayList.size) {

                            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
                            val currentDateFormat = dateFormat.parse(getCurrentDate())
                             val matchDate = dateFormat.parse(convertDateFormat(dataArrayList[i].playingDate))
                             if(matchDate.equals(currentDateFormat) || matchDate.after(currentDateFormat)){
                                 Log.i("Date",convertDateFormat(dataArrayList[i].playingDate))
                                date.postValue(convertDateFormat(dataArrayList[i].playingDate))
                                 break
                            }

                        }
                    }
                     withContext(Dispatchers.IO) {
                        localRepositoryImp.addAllMatches(dataArrayList.toList())
                    }
                }
            } catch (ex: Exception) {

            }
        }
    }

    fun convertDateFormat(input: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        inputFormat.timeZone = TimeZone.getTimeZone("GMT")
        val date = inputFormat.parse(input)
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        outputFormat.timeZone = TimeZone.getDefault()
        return outputFormat.format(date)
    }
    fun getCurrentDate(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val date = Date()
        return formatter.format(date)
    }
    fun getListOfLocalFavMatches() {
        favList.addSource(_favList) {
            favList.value = it
        }
    }


    suspend fun saveFixtures(favMatch: Favorite) {
        withContext(Dispatchers.IO) {
            localRepositoryImp.addFavoriteMatches(favMatch)
        }

    }

    suspend fun removeSourceFromFav(id: Long) {
        withContext(Dispatchers.IO) {
            localRepositoryImp.deleteFavoriteById(id)
        }

    }

    suspend fun updateFlag(flag: Boolean, id: Long) {
        withContext(Dispatchers.IO) {
            localRepositoryImp.updateFlag(flag, id)
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