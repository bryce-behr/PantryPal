package com.example.pantrypal.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.pantrypal.database.Recipe

class RecipeScreenVM() : ViewModel() {
    var recipe: Recipe by mutableStateOf(Recipe(0, 0, "", "", "", ""))
        private set

    fun ChangeRecipeTo(recipe: Recipe) {
        this.recipe = recipe.copy()
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