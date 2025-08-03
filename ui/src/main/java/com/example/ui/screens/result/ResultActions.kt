package com.example.ui.screens.result

data class ResultActions(
    val onPlayAgainClick: () -> Unit = {},
    val onViewHistoryClick: () -> Unit = {},
    val onBackToHomeClick: () -> Unit = {},
) 