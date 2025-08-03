package com.example.ui.screens.result

import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.R
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.data.model.Question
import com.example.ui.navigation.History
import com.example.ui.navigation.Quiz
import com.example.ui.theme.DailyQuizTheme
import com.example.ui.theme.MainBackground
import com.example.ui.ui_kit.DailyQuizCard
import com.example.ui.ui_kit.PrimaryButton
import com.example.ui.ui_kit.ResultQuestionPane
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

private fun getResultTitle(score: Int, totalQuestions: Int): Pair<String, String> {
    return when {
        score == totalQuestions -> "Идеально!" to "$score/$totalQuestions — вы ответили на всё правильно. Это блестящий результат!"
        score == totalQuestions - 1 -> "Почти идеально!" to "$score/$totalQuestions — очень близко к совершенству. Ещё один шаг!"
        score == totalQuestions - 2 -> "Хороший результат!" to "$score/$totalQuestions — вы на верном пути. Продолжайте тренироваться!"
        score == totalQuestions - 3 -> "Есть над чем поработать" to "$score/$totalQuestions — не расстраивайтесь, попробуйте ещё раз!"
        score == totalQuestions - 4 -> "Сложный вопрос?" to "$score/$totalQuestions — иногда просто не ваш день. Следующая попытка будет лучше!"
        else -> "Бывает и так!" to "$score/$totalQuestions — не отчаивайтесь. Начните заново и удивите себя!"
    }
}

@Composable
fun ResultScreen(
    navController: NavController,
    quizResultId: Long,
    viewModel: ResultViewModel = koinViewModel { parametersOf(quizResultId) }
) {
    val uiState by viewModel.getUiState().collectAsStateWithLifecycle()
    val uiActions = ResultActions(
        onPlayAgainClick = { 
            navController.navigate(Quiz) {
                popUpTo(Quiz) { inclusive = true }
            }
        },
        onViewHistoryClick = { navController.navigate(History) },
        onBackToHomeClick = { 
            navController.navigate(Quiz) {
                popUpTo(Quiz) { inclusive = true }
            }
        }
    )

    ResultScreenContent(
        state = uiState,
        actions = uiActions
    )
}

@Composable
private fun ResultScreenContent(
    state: ResultState,
    actions: ResultActions,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MainBackground)
            .padding(20.dp)
    ) {
        // Header with back button
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Результаты",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Категория: ${state.category}",
                        fontSize = 14.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Сложность: ${state.difficulty}",
                        fontSize = 14.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Score Card
            item {
                val (title, subtitle) = getResultTitle(state.score, state.totalQuestions)
                
                DailyQuizCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(modifier = Modifier.weight(1f))
                            repeat(state.totalQuestions) { index ->
                                Image(
                                    painter = painterResource(
                                        if (index < state.score) R.drawable.ic_active_star 
                                        else R.drawable.ic_inactive_star
                                    ),
                                    contentDescription = null,
                                    modifier = Modifier.size(46.dp)
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text(
                            text = title,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = subtitle,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                            textAlign = TextAlign.Center
                        )
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        PrimaryButton(
                            text = "Начать заново",
                            onClick = actions.onPlayAgainClick,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            itemsIndexed(state.questions) { index, question ->
                val userAnswer = if (index < state.userAnswers.size) state.userAnswers[index] else -1
                
                ResultQuestionPane(
                    question = question,
                    totalQuestions = state.totalQuestions,
                    selectedAnswerIndex = userAnswer,
                    correctAnswerIndex = question.correctAnswerIndex,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ResultScreenPreview() {
    val dummyQuestions = listOf(
        Question(
            number = 1,
            text = "What is the capital of France?",
            answers = listOf("London", "Berlin", "Paris", "Madrid"),
            correctAnswerIndex = 2
        ),
        Question(
            number = 2,
            text = "Which planet is known as the Red Planet?",
            answers = listOf("Venus", "Mars", "Jupiter", "Saturn"),
            correctAnswerIndex = 1
        )
    )
    
    DailyQuizTheme {
        ResultScreenContent(
            state = ResultState(
                score = 4,
                totalQuestions = 5,
                timeSpent = "05:30",
                correctAnswers = 4,
                wrongAnswers = 1,
                difficulty = "Легкая",
                category = "Общие знания",
                questions = dummyQuestions,
                userAnswers = listOf(2, 1, 1, 2, 2) // 4 correct, 1 wrong
            ),
            actions = ResultActions()
        )
    }
} 