package com.example.theenglishpremierleague

import android.app.Application
import androidx.room.Room
import com.example.theenglishpremierleague.ui.data.Repository
import com.example.theenglishpremierleague.ui.data.RepositoryImp
import com.example.theenglishpremierleague.ui.data.local.LocalRepository
import com.example.theenglishpremierleague.ui.data.local.LocalRepositoryImp
import com.example.theenglishpremierleague.ui.data.local.MatchesDB
import com.example.theenglishpremierleague.ui.data.remote.RemoteRepository
import com.example.theenglishpremierleague.ui.data.remote.RemoteRepositoryImp
import com.example.theenglishpremierleague.ui.data.remote.ServerAPIs
import com.example.theenglishpremierleague.ui.presentation.MatchesViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        val BASE_URL = "https://api.football-data.org/"
        val API_KEY = "eeaf1766a5b74afa8ad221196853c1be"
        // moshi is a lib used to convert json response to string
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()


        val viewModelModule = module {
            //Declare a ViewModel - be later inject into Fragment with dedicated injector using by viewModel()
            viewModel {
                MatchesViewModel(get())
            }

        }

        val repositoryModule = module {
            //Declare a all repositories - be later inject into Fragment with dedicated injector
            single<RemoteRepository> { RemoteRepositoryImp(api = get()) }
            single<LocalRepository> { LocalRepositoryImp(database = get()) }
            single<Repository> { RepositoryImp(local = get(), remote = get()) }

        }

        val serviceApiModule = module {
            fun getRetrofit(): Retrofit {
                return Retrofit.Builder()
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            single { getRetrofit() }

            fun getServiceAPIInstance(retrofit: Retrofit): ServerAPIs {
                return retrofit.create(ServerAPIs::class.java)
            }

            single { getServiceAPIInstance(retrofit = get()) }
        }

        val dataBaseModule = module {
            fun getDataBaseInstance(application: Application): MatchesDB {
                return Room.databaseBuilder(
                    application,
                    MatchesDB::class.java, "MATCHES_DATA_BASE"
                )
                    .build()
            }
            single { getDataBaseInstance(androidApplication()) }
        }


        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            // List of modules that you need to inject
            modules(
                listOf(
                    viewModelModule, repositoryModule, serviceApiModule,dataBaseModule
                )
            )
        }
    }
}