package com.example.data.model

data class Question(
    val number: Int,
    val text: String,
    val answers: List<String>,
    val correctAnswerIndex: Int
) 