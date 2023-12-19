package com.example.pantrypal.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.pantrypal.database.Recipe

data class RecipeScreenState(
    var recipe: Recipe = Recipe(0, 0, "", "", "", "")
)

class RecipeScreenVM() : ViewModel() {
    var recipeScreeState: RecipeScreenState by mutableStateOf(RecipeScreenState())

    fun ChangeRecipeTo(recipe: Recipe) {
        recipeScreeState = recipeScreeState.copy(recipe = recipe)
    }

    companion object{
        private var INSTANCE: RecipeScreenVM? = null

        fun getInstance(): RecipeScreenVM{
            val vm = INSTANCE ?: synchronized(this){
                RecipeScreenVM().also{
                    INSTANCE = it
                }
            }
            return vm
        }
    }
}