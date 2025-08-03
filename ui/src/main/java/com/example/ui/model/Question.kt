package com.example.ui.model

data class Question(
    val number: Int,
    val text: String,
    val answers: List<String>,
    val correctAnswerIndex: Int // Index of the correct answer in the answers list
)
