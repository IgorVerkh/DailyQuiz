package com.example.ui.screens.history

import com.example.data.model.QuizResult as DataQuizResult
import com.example.ui.screens.history.QuizResult as UiQuizResult
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object QuizResultMapper {
    
    private val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    
    fun mapDataToUi(dataQuizResult: DataQuizResult): UiQuizResult {
        return UiQuizResult(
            id = dataQuizResult.id.toString(),
            date = dateFormat.format(Date(dataQuizResult.dateTime)),
            score = dataQuizResult.score,
            totalQuestions = dataQuizResult.totalQuestions
        )
    }
    
    fun mapDataListToUi(dataQuizResults: List<DataQuizResult>): List<UiQuizResult> {
        return dataQuizResults.map { mapDataToUi(it) }
    }
} 