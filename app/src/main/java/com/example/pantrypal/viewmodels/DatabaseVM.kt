package com.example.pantrypal.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pantrypal.App
//import com.example.pantrypal.App
import com.example.pantrypal.database.PantryPalDAO
import com.example.pantrypal.database.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class DatabaseState(
    var searching: Boolean = false,
    var searchPhrase: String = "",
    var searchedRecipes: List<Recipe> = emptyList()
)

class DatabaseVM(
    private val pantryPalDAO: PantryPalDAO
): ViewModel(){
    var databaseState: DatabaseState by mutableStateOf(DatabaseState())
        private set

//    val searchedRecipes = pantryPalDAO.searchRecipes("%cobb%")
//        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

//    val searchedRecipes = pantryPalDAO.searchRecipes("%" + databaseState.searchPhrase + "%")
//        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val recipes = pantryPalDAO.getAllRecipes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val recipeAndImageIDS = pantryPalDAO.getALLRecipeAndImageIDs()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val titles = pantryPalDAO.getAllTitles()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun getSearched(){
        viewModelScope.launch {
            val searchedRecipes = pantryPalDAO.searchRecipes("%" + databaseState.searchPhrase + "%")
            databaseState = databaseState.copy(searchedRecipes = searchedRecipes)
        }
    }

    fun updateSearching(flag: Boolean){
        databaseState = databaseState.copy(searching = flag)
    }

    fun updateSearchPhrase(text: String){
        databaseState = databaseState.copy(searchPhrase = text)
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

    fun deleteRecipe(id: Int){
        viewModelScope.launch {
            pantryPalDAO.deleteRecipe(id)
        }
    }

    fun deleteRecipe(text: String){
        viewModelScope.launch {
            pantryPalDAO.deleteRecipe(text)
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