package com.example.ui.screens.history

data class HistoryState(
    val quizResults: List<QuizResult> = emptyList(),
    val isLoading: Boolean = false,
)

data class QuizResult(
    val id: String,
    val date: String,
    val score: Int,
    val totalQuestions: Int,
) 