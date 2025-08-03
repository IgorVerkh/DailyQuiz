package com.example.ui.screens.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.data.model.Question
import com.example.ui.navigation.History
import com.example.ui.navigation.Result
import com.example.ui.screens.quiz.components.QuizFilterContent
import com.example.ui.screens.quiz.components.QuizGameContent
import com.example.ui.screens.quiz.components.QuizLoadingContent
import com.example.ui.screens.quiz.components.QuizTimeoutOverlay
import com.example.ui.screens.quiz.components.QuizWelcomeContent
import com.example.ui.theme.DailyQuizTheme
import com.example.ui.theme.MainBackground
import org.koin.androidx.compose.koinViewModel


@Composable
fun QuizScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: QuizViewModel = koinViewModel()
) {
    val uiState by viewModel.getUiState().collectAsStateWithLifecycle()
    
    // Handle navigation based on state changes
    LaunchedEffect(uiState) {
        when (uiState) {
            is QuizState.QuizCompleted -> {
                navController.navigate(Result((uiState as QuizState.QuizCompleted).resultId))
            }
            else -> {}
        }
    }
    
    val uiActions = QuizActions(
        onStartClick = viewModel::onStartClick,
        onAnswerSelected = viewModel::onAnswerSelected,
        onNextClick = viewModel::onNextClick,
        onFinishQuiz = viewModel::onFinishQuiz,
        onCategorySelected = viewModel::onCategorySelected,
        onDifficultySelected = viewModel::onDifficultySelected,
        onStartQuizClick = viewModel::onStartQuizClick,
        onHistoryClick = { navController.navigate(History) },
        onTimeoutRestart = viewModel::onTimeoutRestart
    )

    QuizScreenContent(
        state = uiState,
        actions = uiActions,
        modifier = modifier
    )
}

@Composable
private fun QuizScreenContent(
    state: QuizState,
    actions: QuizActions,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MainBackground)
            .padding(20.dp)
    ) {
        when (state) {
            is QuizState.Welcome -> {
                QuizWelcomeContent(
                    onStartClick = actions.onStartClick,
                    onHistoryClick = actions.onHistoryClick
                )
            }
            is QuizState.Loading -> {
                QuizLoadingContent()
            }
            is QuizState.Filter -> {
                QuizFilterContent(
                    selectedCategory = state.selectedCategory,
                    selectedDifficulty = state.selectedDifficulty,
                    onCategorySelected = actions.onCategorySelected,
                    onDifficultySelected = actions.onDifficultySelected,
                    onStartQuizClick = actions.onStartQuizClick
                )
            }
            is QuizState.Quiz -> {
                QuizGameContent(
                    currentQuestionIndex = state.currentQuestionIndex,
                    totalQuestions = state.totalQuestions,
                    passedTimeSeconds = state.passedTimeSeconds,
                    totalTimeSeconds = state.totalTimeSeconds,
                    selectedAnswerIndex = state.selectedAnswerIndex,
                    questions = state.questions,
                    onAnswerSelected = actions.onAnswerSelected,
                    onNextClick = actions.onNextClick,
                    onFinishQuiz = actions.onFinishQuiz
                )
            }
            is QuizState.QuizCompleted -> { }
            is QuizState.QuizTimeout -> {
                QuizTimeoutOverlay(
                    onRestartClick = actions.onTimeoutRestart
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun QuizScreenWelcomePreview() {
    DailyQuizTheme {
        QuizScreenContent(
            state = QuizState.Welcome,
            actions = QuizActions(),
            modifier = Modifier
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun QuizScreenFilterPreview() {
    DailyQuizTheme {
        QuizScreenContent(
            state = QuizState.Filter(
                selectedCategory = null,
                selectedDifficulty = "easy"
            ),
            actions = QuizActions(),
            modifier = Modifier
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun QuizScreenPreview() {
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
        QuizScreenContent(
            state = QuizState.Quiz(
                questions = dummyQuestions,
                userAnswers = listOf(2, -1)
            ),
            actions = QuizActions()
        )
    }
}

