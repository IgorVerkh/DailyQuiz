package com.example.ui.screens.quiz

data class QuizActions(
    val onStartClick: () -> Unit = {},
    val onAnswerSelected: (Int) -> Unit = {},
    val onNextClick: () -> Unit = {},
    val onFinishQuiz: () -> Unit = {},
    val onCategorySelected: (Int?) -> Unit = {},
    val onDifficultySelected: (String) -> Unit = {},
    val onStartQuizClick: () -> Unit = {},
    val onHistoryClick: () -> Unit = {},
    val onTimeoutRestart: () -> Unit = {},
)