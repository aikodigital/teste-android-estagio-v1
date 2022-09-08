package com.example.olhovivoaikoproj.di

import com.example.olhovivoaikoproj.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel( get()) }
}