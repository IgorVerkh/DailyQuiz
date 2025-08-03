package com.example.ui.ui_kit

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ui.R
import com.example.data.model.Question
import com.example.ui.theme.DailyQuizTheme
import com.example.ui.theme.ElementBackground

@Composable
fun QuizQuestionPane(
    question: Question,
    passedTimeSeconds: Int,
    totalTimeSeconds: Int,
    totalQuestions: Int,
    selectedAnswerIndex: Int?,
    onAnswerSelected: (Int) -> Unit,
    onNextClicked: () -> Unit,
    modifier: Modifier = Modifier,
    isLastQuestion: Boolean = false
) {
    DailyQuizCard(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            QuizTimer(
                passedTimeSeconds = passedTimeSeconds,
                totalTimeSeconds = totalTimeSeconds
            )

            Text(
                text = "Вопрос ${question.number} из $totalQuestions",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Text(
                text = question.text,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                question.answers.forEachIndexed { index, answer ->
                    AnswerOption(
                        text = answer,
                        onClick = { onAnswerSelected(index) },
                        state = if (selectedAnswerIndex == index) {
                            AnswerOptionState.SELECTED
                        } else {
                            AnswerOptionState.IDLE
                        }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            PrimaryButton(
                text = if (isLastQuestion) "Завершить" else "Next",
                onClick = onNextClicked,
                enabled = selectedAnswerIndex != null
            )
        }
    }
}

@Composable
fun ResultQuestionPane(
    question: Question,
    totalQuestions: Int,
    selectedAnswerIndex: Int,
    correctAnswerIndex: Int,
    modifier: Modifier = Modifier
) {
    val isCorrect = selectedAnswerIndex == correctAnswerIndex
    val resultIcon = if (isCorrect) {
        R.drawable.ic_radio_correct
    } else {
        R.drawable.ic_radio_wrong
    }
    
    DailyQuizCard(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row {
                Text(
                    text = "Вопрос ${question.number} из $totalQuestions",
                    style = MaterialTheme.typography.labelMedium,
                    color = ElementBackground
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(resultIcon),
                    contentDescription = if (isCorrect) "Correct" else "Incorrect",
                    modifier = Modifier.size(20.dp)
                )
            }

            Text(
                text = question.text,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                question.answers.forEachIndexed { index, answer ->
                    val answerState = when (index) {
                        correctAnswerIndex -> AnswerOptionState.CORRECT
                        selectedAnswerIndex -> AnswerOptionState.WRONG
                        else -> AnswerOptionState.IDLE
                    }
                    
                    AnswerOption(
                        text = answer,
                        onClick = { /* No interaction in result mode */ },
                        state = answerState
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun QuizQuestionPanePreview() {
    DailyQuizTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            val sampleQuestion = Question(
                number = 1,
                text = "Выберите правильный перевод фразы «Good morning»",
                answers = listOf(
                    "Доброе утро",
                    "Добрый день",
                    "Добрый вечер",
                    "Спокойной ночи"
                ),
                correctAnswerIndex = 0
            )
            
            QuizQuestionPane(
                question = sampleQuestion,
                passedTimeSeconds = 45,
                totalTimeSeconds = 60,
                totalQuestions = 5,
                selectedAnswerIndex = 1,
                onAnswerSelected = {},
                onNextClicked = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ResultQuestionPanePreview() {
    DailyQuizTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            val sampleQuestion = Question(
                number = 1,
                text = "Выберите правильный перевод фразы «Good morning»",
                answers = listOf(
                    "Доброе утро",
                    "Добрый день",
                    "Добрый вечер",
                    "Спокойной ночи"
                ),
                correctAnswerIndex = 0
            )
            
            ResultQuestionPane(
                question = sampleQuestion,
                totalQuestions = 5,
                selectedAnswerIndex = 1,
                correctAnswerIndex = 0
            )
        }
    }
}