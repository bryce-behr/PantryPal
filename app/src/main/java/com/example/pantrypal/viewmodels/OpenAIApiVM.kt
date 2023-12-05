package com.example.pantrypal.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pantrypal.App
import com.example.pantrypal.apis.OpenAIApi
import kotlinx.coroutines.launch

sealed interface OpenAIApiState{

    data class Success(val chatGPTResponse : String): OpenAIApiState
    object Loading: OpenAIApiState
    object Error: OpenAIApiState

}

class OpenAIApiVM(
    private val openAIApi: OpenAIApi
): ViewModel(){

    var openAIApiState: OpenAIApiState by mutableStateOf(OpenAIApiState.Loading)
        private set

    var recipePrompt: String by mutableStateOf("")
        private set

    fun updateRecipePrompt(newPrompt: String){
        recipePrompt = newPrompt
    }

    fun getRecipe(){
        viewModelScope.launch{
            try{
                val prompt = "" +
                        "\n" + recipePrompt

                val response = OpenAIApi.getResponse(prompt)

                openAIApiState = OpenAIApiState.Success(chatGPTResponse = response)
            } catch (e: Exception){
                println("OpenAIVM error: ${e.printStackTrace()}")
                openAIApiState = OpenAIApiState.Error
            }
        }
    }

    /**
     * Implements singleton
     */
    companion object{
        private var INSTANCE: OpenAIApiVM? = null

        fun getInstance(): OpenAIApiVM {
            val vm = INSTANCE ?: synchronized(this){
                OpenAIApiVM(App.getApp().container.openAIApi).also {
                    INSTANCE = it
                }
            }
            return vm
        }

    }

}