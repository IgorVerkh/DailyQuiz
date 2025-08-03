package com.example.data.util

object QuizUtils {
    
    val difficulties = listOf("easy", "medium", "hard")

    val categories = mapOf(
        9 to "General Knowledge",
        10 to "Entertainment: Books",
        11 to "Entertainment: Film",
        12 to "Entertainment: Music",
        13 to "Entertainment: Musicals & Theatres",
        14 to "Entertainment: Television",
        15 to "Entertainment: Video Games",
        16 to "Entertainment: Board Games",
        17 to "Science & Nature",
        18 to "Science: Computers",
        19 to "Science: Mathematics",
        20 to "Mythology",
        21 to "Sports",
        22 to "Geography",
        23 to "History",
        24 to "Politics",
        25 to "Art",
        26 to "Celebrities",
        27 to "Animals",
        28 to "Vehicles",
        29 to "Entertainment: Comics",
        30 to "Science: Gadgets",
        31 to "Entertainment: Japanese Anime & Manga",
        32 to "Entertainment: Cartoon & Animations"
    )
    
    fun getCategoryName(categoryId: Int): String {
        return categories[categoryId] ?: "Unknown Category"
    }
    
    fun isValidDifficulty(difficulty: String): Boolean {
        return difficulties.contains(difficulty.lowercase())
    }
    
    fun isValidCategory(categoryId: Int): Boolean {
        return categories.containsKey(categoryId)
    }
} 