package com.example.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OpenTdbResponse(
    @SerialName("response_code")
    val responseCode: Int,
    @SerialName("results")
    val results: List<OpenTdbQuestion>
)

@Serializable
data class OpenTdbQuestion(
    @SerialName("type")
    val type: String,
    @SerialName("difficulty")
    val difficulty: String,
    @SerialName("category")
    val category: String,
    @SerialName("question")
    val question: String,
    @SerialName("correct_answer")
    val correctAnswer: String,
    @SerialName("incorrect_answers")
    val incorrectAnswers: List<String>
) 