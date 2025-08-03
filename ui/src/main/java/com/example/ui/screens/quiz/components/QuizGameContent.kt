package com.example.ui.screens.quiz.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ui.R
import com.example.data.model.Question
import com.example.ui.ui_kit.QuizQuestionPane

@Composable
fun QuizGameContent(
    currentQuestionIndex: Int,
    totalQuestions: Int,
    passedTimeSeconds: Int,
    totalTimeSeconds: Int,
    selectedAnswerIndex: Int?,
    questions: List<Question>,
    onAnswerSelected: (Int) -> Unit,
    onNextClick: () -> Unit,
    onFinishQuiz: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(180.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        if (questions.isNotEmpty() && currentQuestionIndex < questions.size) {
            val currentQuestion = questions[currentQuestionIndex]
            QuizQuestionPane(
                question = currentQuestion,
                passedTimeSeconds = passedTimeSeconds,
                totalTimeSeconds = totalTimeSeconds,
                totalQuestions = totalQuestions,
                selectedAnswerIndex = selectedAnswerIndex,
                onAnswerSelected = onAnswerSelected,
                onNextClicked = if (currentQuestionIndex == questions.size - 1) onFinishQuiz else onNextClick,
                isLastQuestion = currentQuestionIndex == questions.size - 1
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.cant_navigate_prev_question_hint),
            style = MaterialTheme.typography.bodySmall,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun QuizGameContentPreview() {
    val dummyQuestions = listOf(
        Question(
            number = 1,
            text = "Выберите правильный перевод фразы «Good morning»",
            answers = listOf(
                "Доброе утро",
                "Добрый день",
                "Добрый вечер",
                "Спокойной ночи"
            ),
            correctAnswerIndex = 0
        ),
        Question(
            number = 2,
            text = "Какой цвет получается при смешивании красного и синего?",
            answers = listOf(
                "Зеленый",
                "Желтый",
                "Фиолетовый",
                "Оранжевый"
            ),
            correctAnswerIndex = 2
        ),
        Question(
            number = 3,
            text = "Столица России?",
            answers = listOf(
                "Санкт-Петербург",
                "Москва",
                "Новосибирск",
                "Екатеринбург"
            ),
            correctAnswerIndex = 1
        )
    )

    QuizGameContent(
        currentQuestionIndex = 0,
        totalQuestions = 3,
        passedTimeSeconds = 45,
        totalTimeSeconds = 300,
        selectedAnswerIndex = 1,
        questions = dummyQuestions,
        onAnswerSelected = {},
        onNextClick = {},
        onFinishQuiz = {}
    )
} 