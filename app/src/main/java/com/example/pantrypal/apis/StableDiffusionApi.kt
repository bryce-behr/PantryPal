package com.example.pantrypal.apis

import com.example.pantrypal.models.ImageGenerationRequest
import com.example.pantrypal.models.ImageGenerationResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

//David's key: "1jjmHC8ncHGmmo8zhG89tMTmkLs7z8GBGYXRCYXaGb2JPZMXCmfMTJRLcK16"

interface StableDiffusionApiService{
    @Headers(
        "Content-Type: application/json"
    )
    @POST("api/v3/text2img")
    suspend fun getImage(@Body request: ImageGenerationRequest): ImageGenerationResponse
}

object StableDiffusionApi{
    private val httpClient = OkHttpClient.Builder()
        .callTimeout(100, TimeUnit.SECONDS)
        .readTimeout(100, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json{ ignoreUnknownKeys = true}.asConverterFactory("application/json".toMediaType()))
        .baseUrl("https://stablediffusionapi.com")
        .client(httpClient)
        .build()

    val retrofitService: StableDiffusionApiService by lazy{
        retrofit.create(StableDiffusionApiService::class.java)
    }

    suspend fun getImageUrl(prompt: String): String{
        try {
            val stRequest = ImageGenerationRequest(
                key = "1jjmHC8ncHGmmo8zhG89tMTmkLs7z8GBGYXRCYXaGb2JPZMXCmfMTJRLcK16",
                prompt = prompt,
                samples = "1",
                width = "512",
                height = "512",
                num_inference_steps = "20",
                guidance_scale = 7.5,
                safety_checker = "yes"
            )

            val stResponse = retrofitService.getImage(stRequest)

            return stResponse.output[0]
        } catch (e: Exception){
            e.printStackTrace()
            return ("error")
        }
    }
}