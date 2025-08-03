package com.example.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ui.screens.history.HistoryScreen
import com.example.ui.screens.quiz.QuizScreen
import com.example.ui.screens.result.ResultScreen
import androidx.navigation.toRoute

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController, 
        startDestination = Quiz,
        modifier = modifier
    ) {
        composable<Quiz> { 
            QuizScreen(navController = navController) 
        }
        composable<History> { 
            HistoryScreen(navController = navController) 
        }
        composable<Result> { 
            val args = it.toRoute<Result>()
            ResultScreen(
                navController = navController,
                quizResultId = args.quizResultId
            )
        }
    }
} 