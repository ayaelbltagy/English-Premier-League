package com.example.theenglishpremierleague

import android.app.Application
import com.example.theenglishpremierleague.dependancies.AppModules
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.dsl.module

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()


        startKoin {
            androidContext(this@MyApp)
            // List of modules that you need to inject
            modules(listOf( ))
         }
    }
}