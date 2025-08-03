package com.example.data.remote

import com.example.data.model.OpenTdbResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenTdbApiService {
    
    @GET("api.php")
    suspend fun fetchQuiz(
        @Query("amount") amount: Int = 5,
        @Query("category") category: Int? = null,
        @Query("difficulty") difficulty: String? = null
    ): OpenTdbResponse
} 