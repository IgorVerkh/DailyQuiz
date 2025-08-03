package com.example.ui.screens.quiz.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ui.R
import com.example.ui.theme.DailyQuizTheme
import com.example.ui.ui_kit.DailyQuizCard
import com.example.ui.ui_kit.HistoryButton
import com.example.ui.ui_kit.PrimaryButton

@Composable
fun QuizWelcomeContent(
    onStartClick: () -> Unit,
    onHistoryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HistoryButton(
            text = stringResource(R.string.history),
            onClick = onHistoryClick,
            modifier = Modifier.size(width = 120.dp, height = 40.dp)
        )
        
        Spacer(modifier = Modifier.height(116.dp))
        
        Image(
            painter = painterResource(R.drawable.ic_logo),
            contentDescription = null,
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        DailyQuizCard {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    text = stringResource(R.string.welcome_title),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                PrimaryButton(
                    text = stringResource(R.string.start_quiz_button),
                    onClick = onStartClick
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun QuizWelcomeContentPreview() {
    DailyQuizTheme {
        QuizWelcomeContent(
            onStartClick = {},
            onHistoryClick = {}
        )
    }
} 