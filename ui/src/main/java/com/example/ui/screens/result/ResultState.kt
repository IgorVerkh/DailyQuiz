package com.example.ui.screens.result

import com.example.data.model.Question

data class ResultState(
    val score: Int = 0,
    val totalQuestions: Int = 0,
    val timeSpent: String = "",
    val correctAnswers: Int = 0,
    val wrongAnswers: Int = 0,
    val difficulty: String = "",
    val category: String = "",
    val questions: List<Question> = emptyList(),
    val userAnswers: List<Int> = emptyList(),
    val isLoading: Boolean = false,
) 