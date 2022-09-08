package com.andreesperanca.deolhonobus.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andreesperanca.deolhonobus.repositories.BusDetailsRepository

class BusDetailsViewModelFactory constructor(
    private val repository: BusDetailsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(BusDetailsViewModel::class.java)) {
            BusDetailsViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}