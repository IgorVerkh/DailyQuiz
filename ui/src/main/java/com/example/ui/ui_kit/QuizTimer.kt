package com.example.ui.ui_kit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ui.theme.AccentColor
import com.example.ui.theme.DailyQuizTheme
import com.example.ui.theme.PaneBackground

@Composable
fun QuizTimer(
    passedTimeSeconds: Int,
    totalTimeSeconds: Int,
    modifier: Modifier = Modifier
) {
    val progress = passedTimeSeconds.toFloat() / totalTimeSeconds.toFloat()

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = formatTime(passedTimeSeconds),
                style = MaterialTheme.typography.labelMedium,
                color = AccentColor
            )

            Text(
                text = formatTime(totalTimeSeconds),
                style = MaterialTheme.typography.labelMedium,
                color = AccentColor
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(PaneBackground)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .height(8.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(AccentColor)
            )
        }
    }
}

private fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}

@Preview(showBackground = true)
@Composable
private fun QuizTimerPreview() {
    DailyQuizTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            QuizTimer(
                passedTimeSeconds = 270,
                totalTimeSeconds = 300
            )

            QuizTimer(
                passedTimeSeconds = 60,
                totalTimeSeconds = 300
            )

            QuizTimer(
                passedTimeSeconds = 15,
                totalTimeSeconds = 300
            )

            QuizTimer(
                passedTimeSeconds = 0,
                totalTimeSeconds = 300
            )
        }
    }
}