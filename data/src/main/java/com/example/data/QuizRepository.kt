package com.example.data

import com.example.data.model.QuizResult
import com.example.data.model.Question
import kotlinx.coroutines.flow.Flow

interface QuizRepository {

    suspend fun fetchQuiz(category: Int? = null, difficulty: String = "easy"): List<Question>?

    fun getHistory(): Flow<List<QuizResult>>

    suspend fun saveResult(quizResult: QuizResult): Long

    suspend fun deleteResult(quizResult: QuizResult)

    suspend fun deleteResultById(id: Long)
    
    suspend fun getResultById(id: Long): QuizResult?
}