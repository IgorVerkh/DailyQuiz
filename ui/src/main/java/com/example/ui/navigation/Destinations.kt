package com.example.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object Quiz

@Serializable
object History

@Serializable
data class Result(val quizResultId: Long)