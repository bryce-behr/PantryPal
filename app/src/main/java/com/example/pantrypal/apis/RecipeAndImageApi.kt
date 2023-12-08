package com.example.pantrypal.apis

import com.example.pantrypal.models.RecipeAndImageRecipe
import com.example.pantrypal.models.RecipeAndImageResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

//https://rapidapi.com/zilinskivan/api/food-recipes-with-images

interface RecipeAndImageApiService{
    @Headers(
        "X-RapidAPI-Key: af8eba68b3mshfcdb4d37fc8bb27p115fadjsn1809801dfc9c",
        "X-RapidAPI-Host: food-recipes-with-images.p.rapidapi.com"
    )
    @GET("/")
    suspend fun getResponse(@Query("q") q: String = "liquorice soup"): RecipeAndImageResponse
}

object RecipeAndImageApi{
    private val httpClient = OkHttpClient.Builder()
        .callTimeout(50, TimeUnit.SECONDS)
        .readTimeout(50, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json{ignoreUnknownKeys = true}.asConverterFactory("application/json".toMediaType()))
        .baseUrl("https://food-recipes-with-images.p.rapidapi.com")
        .client(httpClient)
        .build()

    val retrofitService: RecipeAndImageApiService by lazy{
        retrofit.create(RecipeAndImageApiService::class.java)
    }

    suspend fun getResponse(prompt: String): RecipeAndImageResponse{
        try {
            return retrofitService.getResponse(q = prompt)
        } catch (e: Exception){
            e.printStackTrace()
            println("Error: " + e.message)
            throw Exception("HELP")
        }

    }
}

