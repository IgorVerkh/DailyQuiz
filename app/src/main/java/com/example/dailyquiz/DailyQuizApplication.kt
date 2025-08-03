package com.example.dailyquiz

import android.app.Application
import com.example.data.di.dataModule
import com.example.ui.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class DailyQuizApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        startKoin {
            androidLogger()
            androidContext(this@DailyQuizApplication)
            modules(
                dataModule,
                viewModelModule
            )
        }
    }
} 