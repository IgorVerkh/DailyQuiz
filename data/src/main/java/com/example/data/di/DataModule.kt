package com.example.data.di

import com.example.data.QuizRepository
import com.example.data.QuizRepositoryImpl
import com.example.data.local.QuizDatabase
import com.example.data.remote.NetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {

    single { QuizDatabase.getDatabase(androidContext()) }
    single { get<QuizDatabase>().quizResultDao() }
    single { NetworkModule.openTdbApiService }
    single<QuizRepository> {
        QuizRepositoryImpl(
            quizResultDao = get(),
            openTdbApiService = get()
        )
    }
} 