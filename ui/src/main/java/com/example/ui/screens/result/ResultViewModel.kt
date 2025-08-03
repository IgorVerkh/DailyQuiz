package com.example.ui.screens.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.QuizRepository
import com.example.data.model.QuizResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ResultViewModel(
    private val repository: QuizRepository,
    private val quizResultId: Long
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ResultState())
    private val uiState = _uiState
        .onStart { loadData() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ResultState()
        )

    fun getUiState() = uiState

    private fun loadData() {
        viewModelScope.launch {
            try {
                val quizResult = repository.getResultById(quizResultId)
                if (quizResult != null) {
                    _uiState.value = ResultState(
                        score = quizResult.score,
                        totalQuestions = quizResult.totalQuestions,
                        timeSpent = "00:00", // TODO: Calculate from start/end time
                        correctAnswers = quizResult.score,
                        wrongAnswers = quizResult.totalQuestions - quizResult.score,
                        difficulty = quizResult.difficulty,
                        category = quizResult.category,
                        questions = emptyList(), // TODO: Store questions in QuizResult
                        userAnswers = emptyList() // TODO: Store user answers in QuizResult
                    )
                }
            } catch (e: Exception) {
                println("Error loading quiz result: ${e.message}")
            }
        }
    }

    fun onPlayAgainClick() {
        // This will be handled in the UI layer
    }

    fun onViewHistoryClick() {
        // This will be handled in the UI layer
    }

    fun onBackToHomeClick() {
        // This will be handled in the UI layer
    }
} 