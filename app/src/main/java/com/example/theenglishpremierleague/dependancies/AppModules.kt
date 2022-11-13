package com.example.theenglishpremierleague.dependancies

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class AppModules {

    val viewModelModule = module {
        //Declare a ViewModel - be later inject into Fragment with dedicated injector using by viewModel()

    }
}