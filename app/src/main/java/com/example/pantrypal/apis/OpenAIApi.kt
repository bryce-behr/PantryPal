package com.example.pantrypal.apis

import com.example.pantrypal.models.ChatRequest
import com.example.pantrypal.models.ChatResponse
import com.example.pantrypal.models.Message
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface OpenAIApiService {

    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer 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"
    )
    @POST("v1/chat/completions")
    suspend fun getChatCompletions(@Body request: ChatRequest) : ChatResponse

}

object OpenAIApi{

    private val httpClient = OkHttpClient.Builder()
        .callTimeout(50, TimeUnit.SECONDS)
        .readTimeout(50, TimeUnit.SECONDS).build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json{ ignoreUnknownKeys = true}.asConverterFactory("application/json".toMediaType()))
        //.addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl("https://api.openai.com/")
        .client(httpClient)
        .build()

    val retrofitService : OpenAIApiService by lazy{
        retrofit.create(OpenAIApiService::class.java)
    }

    suspend fun getResponse(prompt: String) : String {
        try{
            val chatRequest = ChatRequest(
                model = "gpt-3.5-turbo",
                messages = listOf(
                    Message(
                        role = "user",
                        content = "Hello, how are you?"
                    )//prompt)
                )
            )
            println("before ")
            println("Request Body: $chatRequest")
            val chatResponse = retrofitService.getChatCompletions(chatRequest)
            println("after")
            return chatResponse.choices[0].message.content
        } catch (e: Exception){
            e.printStackTrace()
            return ("Error: " + e.message)
        }
    }

}

//interface OpenAIApiService {
//    @Headers(
//        "Content-Type: application/json",
//        "Authorization: Bearer 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"
//    )
//    @POST("v1/chat/completions")
//    suspend fun getChatCompletion(@Body request: ChatRequest): ChatResponse
//}
//
//object OpenAIApi{
//    private val httpClient = OkHttpClient.Builder()
//        .callTimeout(50, TimeUnit.SECONDS)
//        .readTimeout(50, TimeUnit.SECONDS)
//        .build()
//
//    private val retrofit = Retrofit.Builder()
//        .addConverterFactory(Json{ignoreUnknownKeys = true}.asConverterFactory("application/json".toMediaType()))
//        .baseUrl("https://api.openai.com/")
//        .client(httpClient)
//        .build()
//
//    val retrofitService: OpenAIApiService by lazy{
//        retrofit.create(OpenAIApiService::class.java)
//    }
//
//    suspend fun getResponse(prompt: String): String{
//        val chatRequest = ChatRequest(
//            model = "gpt-3.5-turbo",
//            messages = listOf(
//                Message(
//                    role = "user",
//                    content = "hello" // prompt
//                )
//            )
//        )
//        println("before response")
//        println(chatRequest)
//        val response = retrofitService.getChatCompletion(chatRequest)
//        println("Response")
//        return response.choices[0].message.content
//    }
//}