package com.example.pantrypal.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pantrypal.App
//import com.example.pantrypal.App
import com.example.pantrypal.database.PantryPalDAO
import com.example.pantrypal.database.Recipe
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DatabaseVM(
    private val pantryPalDAO: PantryPalDAO
): ViewModel(){

    val recipes = pantryPalDAO.getAllRecipes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    init{

    }

    fun insertRecipe(recipe: Recipe){
        viewModelScope.launch {
            pantryPalDAO.upsertRecipe(recipe)
        }
    }

    fun deleteRecipe(recipe: Recipe){
        viewModelScope.launch {
            pantryPalDAO.deleteRecipe(recipe)
        }
    }

        fun getRecipesWithID(id: Int){
        viewModelScope.launch {
            pantryPalDAO.getRecipesWithID(id)
        }
    }

    fun getRecipesWithApiID(apiID: Int){
        viewModelScope.launch {
            pantryPalDAO.getRecipesWithApiID(apiID)
        }
    }

    fun getRecipesWithName(name: String){
        viewModelScope.launch {
            pantryPalDAO.getRecipesWithName(name)
        }
    }

    /**
     * Implements singleton
     */
    companion object{
        private var INSTANCE: DatabaseVM? = null

        fun getInstance(): DatabaseVM{
            val vm = INSTANCE?: synchronized(this){
                DatabaseVM(App.getApp().container.pantryPalDAO).also{
                    INSTANCE = it
                }
            }
            return vm
        }
    }
}