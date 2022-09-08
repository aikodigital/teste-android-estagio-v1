package com.example.olhovivoaikoproj.di

import com.example.olhovivoaikoproj.presentation.activity.Adapter
import org.koin.dsl.module

val adapterModule = module {
    factory { Adapter() }
}