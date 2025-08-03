package com.example.ui.di

import com.example.ui.screens.history.HistoryViewModel
import com.example.ui.screens.quiz.QuizViewModel
import com.example.ui.screens.result.ResultViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { QuizViewModel(repository = get()) }
    viewModel { (quizResultId: Long) -> ResultViewModel(repository = get(), quizResultId = quizResultId) }
    viewModel { HistoryViewModel(repository = get()) }
}