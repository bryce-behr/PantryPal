package com.example.pantrypal.apis

import com.example.pantrypal.models.RecipeAndImageRecipe
import com.example.pantrypal.models.RecipeAndImageResponse
import com.example.pantrypal.models.RecipeRequest
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.Headers
import java.util.concurrent.TimeUnit

//https://rapidapi.com/zilinskivan/api/food-recipes-with-images

interface RecipeAndImageApiService{
    @Headers(
        "C-RapidAPI-Key: af8eba68b3mshfcdb4d37fc8bb27p115fadjsn1809801dfc9c",
        "X-RapidAPI-Host: food-recipes-with-images.p.rapidapi.com"
    )
    suspend fun getRecipeCompletion(@Body request: RecipeRequest): RecipeAndImageResponse
}

object RecipeAndImageApi{
    private val httpClient = OkHttpClient.Builder()
        .callTimeout(50, TimeUnit.SECONDS)
        .readTimeout(50, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json{ignoreUnknownKeys = true}.asConverterFactory("application/json".toMediaType()))
        .baseUrl("rapidapi.com")
        .client(httpClient)
        .build()

    val retrofitService: RecipeAndImageApiService by lazy{
        retrofit.create(RecipeAndImageApiService::class.java)
    }

    suspend fun getResponse(prompt: String): RecipeAndImageResponse{
        val recipeRequest = RecipeRequest(
            recipeApiRequest = prompt
        )
        val recipeResponse = retrofitService.getRecipeCompletion(recipeRequest)
        return recipeResponse
    }
}

