package com.example.pantrypal.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.example.pantrypal.App
import com.example.pantrypal.apis.OpenAIApi
import com.example.pantrypal.apis.StableDiffusionApi
import kotlinx.coroutines.launch

sealed interface StableDiffusionState{
    data class Success(val imageUrl: String): StableDiffusionState
    object Loading: StableDiffusionState
    object Error: StableDiffusionState
}

class StableDiffusionVM(): ViewModel(){

    init {
        println("test")
    }

    var stableDiffusionState: StableDiffusionState by mutableStateOf(StableDiffusionState.Loading)
        private set

    var drawingText: String by mutableStateOf("")
        private set

    fun updateDrawText(newText: String){
        drawingText = newText
    }

    fun getRecipeImage(){
        viewModelScope.launch{
            try{
                val prompt = "Please generate a prompt for a text to image ai that will lead the ai to generate an image resembling the follwoing description: " +
                        "\n" + drawingText

                val response = OpenAIApi.getResponse(prompt)

                val imgUrl = StableDiffusionApi.getImageUrl(response)

                stableDiffusionState = StableDiffusionState.Success(imageUrl = imgUrl)
            } catch (e: Exception){
                println("StableDiffusioVM error: ${e.printStackTrace()}")
                stableDiffusionState = StableDiffusionState.Error
            }
        }
    }

}