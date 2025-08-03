package com.example.data

import com.example.data.local.QuizResultDao
import com.example.data.mapper.QuestionMapper
import com.example.data.model.QuizResult
import com.example.data.model.Question
import com.example.data.remote.NetworkModule
import com.example.data.remote.OpenTdbApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

class QuizRepositoryImpl(
    private val quizResultDao: QuizResultDao,
    private val openTdbApiService: OpenTdbApiService = NetworkModule.openTdbApiService
) : QuizRepository {
    
    override suspend fun fetchQuiz(category: Int?, difficulty: String): List<Question>? {
        return try {
            val response = openTdbApiService.fetchQuiz(
                amount = 5,
                category = category,
                difficulty = difficulty
            )
            
            if (response.responseCode == 0) {
                response.results.mapIndexed { index, openTdbQuestion ->
                    QuestionMapper.mapOpenTdbToDomain(openTdbQuestion, index + 1)
                }
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
    
    override fun getHistory(): Flow<List<QuizResult>> {
        return quizResultDao.getAllQuizResults()
            .catch { 
                // Return empty list on error
                emit(emptyList())
            }
    }
    
    override suspend fun saveResult(quizResult: QuizResult): Long {
        return quizResultDao.insertQuizResult(quizResult)
    }
    
    override suspend fun deleteResult(quizResult: QuizResult) {
        quizResultDao.deleteQuizResult(quizResult)
    }
    
    override suspend fun deleteResultById(id: Long) {
        quizResultDao.deleteQuizResultById(id)
    }
    
    override suspend fun getResultById(id: Long): QuizResult? {
        return quizResultDao.getQuizResultById(id)
    }
} 