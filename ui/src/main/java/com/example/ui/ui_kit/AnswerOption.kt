package com.example.ui.ui_kit

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ui.R
import com.example.ui.theme.DailyQuizTheme
import com.example.ui.theme.ErrorColor
import com.example.ui.theme.LightGrayBackground
import com.example.ui.theme.SuccessColor


@Composable
fun AnswerOption(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    state: AnswerOptionState = AnswerOptionState.IDLE
) {
    // TODO: Fix the ripple effect
    val style = when (state) {
        AnswerOptionState.IDLE -> AnswerOptionStyle(
            icon = painterResource(R.drawable.ic_radio_button),
            contentColor = Color.Black,
            backgroundColor = LightGrayBackground,
            borderColor = Color.Transparent
        )
        AnswerOptionState.SELECTED -> AnswerOptionStyle(
            icon = painterResource(R.drawable.ic_radio_selected),
            contentColor = MaterialTheme.colorScheme.secondary,
            backgroundColor = Color.Transparent,
            borderColor = MaterialTheme.colorScheme.secondary
        )
        AnswerOptionState.CORRECT -> AnswerOptionStyle(
            icon = painterResource(R.drawable.ic_radio_correct),
            contentColor = SuccessColor,
            backgroundColor = Color.Transparent,
            borderColor = SuccessColor
        )
        AnswerOptionState.WRONG -> AnswerOptionStyle(
            icon = painterResource(R.drawable.ic_radio_wrong),
            contentColor = ErrorColor,
            backgroundColor = Color.Transparent,
            borderColor = ErrorColor
        )
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(shape = RoundedCornerShape(16.dp))
            .background(color = style.backgroundColor)
            .border(width = 1.dp, color = style.borderColor, shape = RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick() }
    ) {
        Image(
            painter = style.icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )

        Spacer(Modifier.width(16.dp))

        Text(
            text = text,
            color = style.contentColor,
            modifier = Modifier
                .weight(1f)
        )
    }
}


enum class AnswerOptionState {
    IDLE, SELECTED, CORRECT, WRONG
}

private data class AnswerOptionStyle(
    val icon: androidx.compose.ui.graphics.painter.Painter,
    val contentColor: Color,
    val backgroundColor: Color,
    val borderColor: Color
)


@Preview(showBackground = true)
@Composable
private fun AnswerOptionPreview() {
    Column(
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp)
    ) {
        DailyQuizTheme {
            AnswerOption(text = "Яблоко", onClick = {}, state = AnswerOptionState.IDLE)
        }

        DailyQuizTheme {
            AnswerOption(text = "Яблоко", onClick = {}, state = AnswerOptionState.SELECTED)
        }

        DailyQuizTheme {
            AnswerOption(text = "Яблоко", onClick = {}, state = AnswerOptionState.CORRECT)
        }

        DailyQuizTheme {
            AnswerOption(text = "Яблоко", onClick = {}, state = AnswerOptionState.WRONG)
        }
    }
}