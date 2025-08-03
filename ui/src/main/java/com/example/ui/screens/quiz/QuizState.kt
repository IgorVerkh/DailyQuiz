package com.example.ui.screens.quiz

import com.example.data.model.Question

sealed class QuizState {
    object Welcome : QuizState()
    object Loading : QuizState()
    data class Filter(
        val selectedCategory: Int? = null,
        val selectedDifficulty: String = "easy"
    ) : QuizState()
    data class Quiz(
        val currentQuestionIndex: Int = 0,
        val totalQuestions: Int = 5,
        val passedTimeSeconds: Int = 0,
        val totalTimeSeconds: Int = 300,
        val selectedAnswerIndex: Int? = null,
        val questions: List<Question> = emptyList(),
        val userAnswers: List<Int> = emptyList(),
        val category: Int? = null,
        val difficulty: String = "easy",
    ) : QuizState()
    data class QuizCompleted(val resultId: Long) : QuizState()
    data class QuizTimeout(
        val questions: List<Question> = emptyList(),
        val userAnswers: List<Int> = emptyList(),
        val category: Int? = null,
        val difficulty: String = "easy",
    ) : QuizState()
}