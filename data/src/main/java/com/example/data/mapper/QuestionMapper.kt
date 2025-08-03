package com.example.data.mapper

import com.example.data.model.OpenTdbQuestion
import com.example.data.model.Question
import com.example.data.util.HtmlDecoder

object QuestionMapper {
    
    fun mapOpenTdbToDomain(openTdbQuestion: OpenTdbQuestion, questionNumber: Int): Question {

        val allAnswers = (openTdbQuestion.incorrectAnswers + openTdbQuestion.correctAnswer).shuffled()
        val correctAnswerIndex = allAnswers.indexOf(openTdbQuestion.correctAnswer)
        
        return Question(
            number = questionNumber,
            text = HtmlDecoder.decodeHtmlEntities(openTdbQuestion.question),
            answers = allAnswers.map { HtmlDecoder.decodeHtmlEntities(it) },
            correctAnswerIndex = correctAnswerIndex
        )
    }
} 