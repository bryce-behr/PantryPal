package com.example.pantrypal.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pantrypal.App
import com.example.pantrypal.apis.RecipeAndImageApi
import com.example.pantrypal.models.RecipeAndImageResponse
import kotlinx.coroutines.launch

sealed interface RecipeAndImageState{
    data class Success(val recipe: RecipeAndImageResponse): RecipeAndImageState
    object Loading: RecipeAndImageState
    object Error: RecipeAndImageState
}

class RecipeAndImageVM(
    private val recipeAndImageApi: RecipeAndImageApi
): ViewModel(){

    init{
    }

    var recipeAndImageState: RecipeAndImageState by mutableStateOf(RecipeAndImageState.Loading)

    var recipeText: String by mutableStateOf("")

    fun updateRecipeText(newText: String){
        recipeText = newText
    }

    fun getRecipeAndImage(){
        viewModelScope.launch{
            try {
                val out = recipeAndImageApi.getResponse(recipeText)

                recipeAndImageState = RecipeAndImageState.Success(recipe = out)
            } catch (e: Exception) {
                recipeAndImageState = RecipeAndImageState.Error
            }
        }
    }

    companion object{
        private var INSTANCE: RecipeAndImageVM? = null

        fun getInstance(): RecipeAndImageVM {
            val vm = INSTANCE ?: synchronized(this) {
                RecipeAndImageVM(App.getApp().container.recipeAndImageApi).also {
                    INSTANCE = it
                }
            }
            return vm
        }
    }

}