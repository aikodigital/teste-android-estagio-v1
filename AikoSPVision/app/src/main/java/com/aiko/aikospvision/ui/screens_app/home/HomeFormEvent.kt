package com.aiko.aikospvision.ui.screens_app.home

sealed class HomeFormEvent {
    data class SearchChanged(val searchText: String) : HomeFormEvent()
    data object Submit : HomeFormEvent()
}