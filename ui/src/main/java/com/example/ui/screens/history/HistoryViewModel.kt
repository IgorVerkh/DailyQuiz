package com.example.ui.screens.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.QuizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val repository: QuizRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(HistoryState())
    private val uiState = _uiState
        .onStart { loadData() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            HistoryState()
        )

    fun getUiState() = uiState

    private fun loadData() {
        viewModelScope.launch {
            try {
                repository.getHistory().collect { dataQuizResults ->
                    val uiQuizResults = QuizResultMapper.mapDataListToUi(dataQuizResults)
                    _uiState.value = HistoryState(
                        isLoading = false,
                        quizResults = uiQuizResults
                    )
                }
            } catch (e: Exception) {
                _uiState.value = HistoryState(
                    isLoading = false,
                    quizResults = emptyList()
                )
            }
        }
    }
} 