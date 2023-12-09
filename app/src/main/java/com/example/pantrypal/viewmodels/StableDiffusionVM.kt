package com.example.pantrypal.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pantrypal.App
//import com.example.pantrypal.App
import com.example.pantrypal.apis.OpenAIApi
import com.example.pantrypal.apis.StableDiffusionApi
import kotlinx.coroutines.launch

sealed interface StableDiffusionState{
    data class Success(val imageUrl: String): StableDiffusionState
    object Loading: StableDiffusionState
    object Error: StableDiffusionState
}

class StableDiffusionVM(
    private val stableDiffusionApi: StableDiffusionApi
): ViewModel(){

    init {
        println("test")
    }

    var stableDiffusionState: StableDiffusionState by mutableStateOf(StableDiffusionState.Loading)
        private set

    var recipeText: String by mutableStateOf("")
        private set

    fun updateDrawText(newText: String){
        recipeText = newText
    }

    fun getRecipeImage(){
        viewModelScope.launch{
            try{
                val prompt = "Can you please provide a prompt for a text to image ai that will result in it generating a photo-realistic picture  for a cook book for the following recipe:" +
                        "\n" + recipeText + "\n" +
                        "Please only respond with the prompt for the text to image ai and take extra care to ensure that the image will resemble exactly what the recipe will create. Please respond only with the prompt for the text to image ai."

                val response = OpenAIApi.getResponse(prompt)

                val imgUrl = stableDiffusionApi.getImageUrl(response)

                stableDiffusionState = StableDiffusionState.Success(imageUrl = imgUrl)
            } catch (e: Exception){
                println("StableDiffusioVM error: ${e.printStackTrace()}")
                stableDiffusionState = StableDiffusionState.Error
            }
        }
    }

    companion object{
        private var INSTANCE: StableDiffusionVM? = null

        fun getInstance(): StableDiffusionVM {
            val vm = INSTANCE ?: synchronized(this) {
                StableDiffusionVM(App.getApp().container.stableDiffusionApi).also {
                    INSTANCE = it
                }
            }
            return vm
        }
    }

}