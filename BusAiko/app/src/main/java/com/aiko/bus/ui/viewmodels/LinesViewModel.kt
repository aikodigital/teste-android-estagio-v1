package com.aiko.bus.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aiko.bus.models.Line
import com.aiko.bus.repositories.LineRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LinesViewModel: ViewModel() {
    private val repository = LineRepository()

    private val _lines = MutableStateFlow<List<Line>>(emptyList())
    private val _isLoading = MutableStateFlow(false)

    val lines: StateFlow<List<Line>> = _lines
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadLines(q: String) {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                _lines.value = repository.getLines(q)
            } catch (e: Exception) {
                // Handle error
                Log.e("LinesViewModel", e.toString())
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getLineById(id: Int): Line {
        val line = lines.value.find { it.id == id }
        Log.e("Line", line.toString())
        return line ?: throw Exception("Line not found")
    }

}