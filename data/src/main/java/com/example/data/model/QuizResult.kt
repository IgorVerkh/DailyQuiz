package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_results")
data class QuizResult(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val score: Int,
    val totalQuestions: Int,
    val dateTime: Long,
    val difficulty: String,
    val category: String,
    val categoryId: Int
) 