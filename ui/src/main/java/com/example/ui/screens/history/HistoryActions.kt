package com.example.ui.screens.history

data class HistoryActions(
    val onQuizResultClick: (String) -> Unit = {},
    val onBackClick: () -> Unit = {},
) 