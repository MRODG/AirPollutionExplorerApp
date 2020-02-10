package com.mariosodigie.apps.airpollutionexplore.di

import com.mariosodigie.apps.airpollutionexplore.explorefeature.ExplorerViewModel
import com.mariosodigie.apps.airpollutionexplore.explorefeature.PollutionDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { ExplorerViewModel(get(), get()) }
    viewModel { PollutionDetailsViewModel(get(),get()) }
}