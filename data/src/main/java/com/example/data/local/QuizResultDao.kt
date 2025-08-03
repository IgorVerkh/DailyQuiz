package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.QuizResult
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizResultDao {
    
    @Query("SELECT * FROM quiz_results ORDER BY dateTime DESC")
    fun getAllQuizResults(): Flow<List<QuizResult>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizResult(quizResult: QuizResult): Long
    
    @Delete
    suspend fun deleteQuizResult(quizResult: QuizResult)
    
    @Query("DELETE FROM quiz_results WHERE id = :id")
    suspend fun deleteQuizResultById(id: Long)
    
    @Query("SELECT * FROM quiz_results WHERE id = :id")
    suspend fun getQuizResultById(id: Long): QuizResult?
} 