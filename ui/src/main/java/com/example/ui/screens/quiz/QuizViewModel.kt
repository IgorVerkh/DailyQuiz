package com.example.ui.screens.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.QuizRepository
import com.example.data.model.QuizResult
import com.example.data.util.QuizUtils
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class QuizViewModel(
    private val repository: QuizRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<QuizState>(QuizState.Welcome)
    private val uiState = _uiState
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            QuizState.Welcome
        )
    
    private var timerJob: Job? = null

    fun getUiState() = uiState

    private fun saveCurrentAnswer(quizState: QuizState.Quiz): List<Int> {
        val updatedUserAnswers = quizState.userAnswers.toMutableList()
        updatedUserAnswers[quizState.currentQuestionIndex] = quizState.selectedAnswerIndex ?: -1
        return updatedUserAnswers
    }
    
    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            var timeElapsed = 0
            while (timeElapsed < 300) { // 5 minutes = 300 seconds
                delay(1000) // Wait 1 second
                timeElapsed++
                
                val currentState = _uiState.value
                if (currentState is QuizState.Quiz) {
                    _uiState.value = currentState.copy(passedTimeSeconds = timeElapsed)
                }
            }
            
            // Time is up!
            val currentState = _uiState.value
            if (currentState is QuizState.Quiz) {
                _uiState.value = QuizState.QuizTimeout(
                    questions = currentState.questions,
                    userAnswers = currentState.userAnswers,
                    category = currentState.category,
                    difficulty = currentState.difficulty
                )
            }
        }
    }
    
    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    fun onStartClick() {
        _uiState.value = QuizState.Filter()
    }

    fun onCategorySelected(category: Int?) {
        val currentState = _uiState.value
        if (currentState is QuizState.Filter) {
            _uiState.value = currentState.copy(selectedCategory = category)
        }
    }

    fun onDifficultySelected(difficulty: String) {
        val currentState = _uiState.value
        if (currentState is QuizState.Filter) {
            _uiState.value = currentState.copy(selectedDifficulty = difficulty)
        }
    }

    fun onStartQuizClick() {
        val currentState = _uiState.value
        if (currentState is QuizState.Filter) {
            _uiState.value = QuizState.Loading
            
            viewModelScope.launch {
                try {
                    val questions = repository.fetchQuiz(
                        category = currentState.selectedCategory,
                        difficulty = currentState.selectedDifficulty
                    )
                    
                    if (questions != null) {
                        _uiState.value = QuizState.Quiz(
                            questions = questions,
                            userAnswers = List(questions.size) { -1 },
                            category = currentState.selectedCategory,
                            difficulty = currentState.selectedDifficulty
                        )
                        startTimer() // Start the timer when quiz begins
                    } else {
                        _uiState.value = currentState
                    }
                } catch (e: Exception) {
                    _uiState.value = currentState
                }
            }
        }
    }

    fun onAnswerSelected(answerIndex: Int) {
        val currentState = _uiState.value
        if (currentState is QuizState.Quiz) {
            _uiState.value = currentState.copy(selectedAnswerIndex = answerIndex)
        }
    }

    fun onNextClick() {
        val currentState = _uiState.value
        if (currentState is QuizState.Quiz) {
            val updatedUserAnswers = saveCurrentAnswer(currentState)
            val nextQuestionIndex = currentState.currentQuestionIndex + 1
            
            if (nextQuestionIndex < currentState.questions.size) {
                _uiState.value = currentState.copy(
                    currentQuestionIndex = nextQuestionIndex,
                    selectedAnswerIndex = null,
                    userAnswers = updatedUserAnswers
                )
            }
        }
    }

    fun onFinishQuiz() {
        val currentState = _uiState.value
        if (currentState is QuizState.Quiz) {
            val updatedUserAnswers = saveCurrentAnswer(currentState)
            
            viewModelScope.launch {
                try {
                    val correctAnswers = currentState.questions.map { it.correctAnswerIndex }
                    val score = updatedUserAnswers.zip(correctAnswers).count { (userAnswer, correctAnswer) ->
                        userAnswer == correctAnswer
                    }
                    
                    val quizResult = QuizResult(
                        score = score,
                        totalQuestions = currentState.questions.size,
                        dateTime = System.currentTimeMillis(),
                        difficulty = currentState.difficulty,
                        category = currentState.category?.let { QuizUtils.getCategoryName(it) }
                            ?: "Random Category",
                        categoryId = currentState.category ?: 0
                    )
                    
                    val resultId = repository.saveResult(quizResult)
                    stopTimer() // Stop the timer when quiz is completed
                    _uiState.value = QuizState.QuizCompleted(resultId)
                    
                } catch (e: Exception) {
                    println("Error saving quiz result: ${e.message}")
                }
            }
        }
    }
    
    fun onTimeoutRestart() {
        _uiState.value = QuizState.Welcome
    }
}