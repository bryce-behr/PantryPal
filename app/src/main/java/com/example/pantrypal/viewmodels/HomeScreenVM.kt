package com.example.pantrypal.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pantrypal.App
import com.example.pantrypal.apis.OpenAIApi
import com.example.pantrypal.apis.RecipeAndImageApi
import com.example.pantrypal.database.Recipe
import com.example.pantrypal.models.RecipeAndImageRecipe
import kotlinx.coroutines.launch

data class HomeScreenState(
    var breakfastRecipes: MutableList<Recipe> = mutableListOf(),
    var lunchRecipes: MutableList<Recipe> = mutableListOf(),
    var dinnerRecipes: MutableList<Recipe> = mutableListOf(),
    var dessertRecipes: MutableList<Recipe> = mutableListOf(),
    var searchRecipes: MutableList<Recipe> = mutableListOf(),
    var largeList: MutableList<Recipe> = mutableListOf(),
    var searchFlag: Boolean = false,
    var breakfastFlag: Boolean = true,
    var lunchFlag: Boolean = true,
    var dinnerFlag: Boolean = true,
    var dessertFlag: Boolean = true,
    var searchPhrase: String = ""
)

class HomeScreenVM(
    private val recipeAndImageHomeScreenApi: RecipeAndImageApi
): ViewModel(){

    var homeScreenState: HomeScreenState by mutableStateOf(HomeScreenState())

/*
    init {
        viewModelScope.launch {
            val breakfasts: List<String> = OpenAIApi.getResponse("please give me the names of the 5 most popular breakfast dishes as a " +
                    "comma-delimited list. Please respond with only the comma-delimited list and do not number the dishes.").split(",")
            var allBreakfasts: MutableList<Recipe> = mutableListOf()
            breakfasts.forEach { x->
                val response = recipeAndImageHomeScreenApi.getResponse(x)
                if (response.numRecipes > 0) {
                    val tempBreakfasts: List<RecipeAndImageRecipe> = response.recipes

                    tempBreakfasts.forEach { y ->
                        allBreakfasts.add(y.toRecipe())
                        homeScreenState = homeScreenState.copy(
                            breakfastRecipes = allBreakfasts,
                            largeList = allBreakfasts
                        )
                    }
                }
            }
            val lunches: List<String> = OpenAIApi.getResponse("please give me the names of the 5 most popular lunch dishes as a " +
                    "comma-delimited list. Please respond with only the comma-delimited list and do not number the dishes.").split(",")
            var allLunches: MutableList<Recipe> = mutableListOf()
            var tempList: MutableList<Recipe> = homeScreenState.largeList
            lunches.forEach { x->
                val response = recipeAndImageHomeScreenApi.getResponse(x)
                if (response.numRecipes > 0) {
                    val tempLunches: List<RecipeAndImageRecipe> = response.recipes
                    tempLunches.forEach { y ->
                        allLunches.add(y.toRecipe())
                        tempList.add(y.toRecipe())
                        homeScreenState = homeScreenState.copy(
                            lunchRecipes = allLunches,
                            largeList = tempList
                        )
                    }
                }
            }
            val dinners: List<String> = OpenAIApi.getResponse("please give me the names of the 5 most popular dinner dishes as a " +
                    "comma-delimited list. Please respond with only the comma-delimited list and do not number the dishes.").split(",")
            var allDinners: MutableList<Recipe> = mutableListOf()
            dinners.forEach { x->
                val response = recipeAndImageHomeScreenApi.getResponse(x)
                if (response.numRecipes > 0) {
                    val tempDinners: List<RecipeAndImageRecipe> = response.recipes
                    tempDinners.forEach { y ->
                        allDinners.add(y.toRecipe())
                        tempList.add(y.toRecipe())
                        homeScreenState = homeScreenState.copy(
                            dinnerRecipes = allDinners,
                            largeList = tempList
                        )
                    }
                }
            }
            val desserts: List<String> = OpenAIApi.getResponse("please give me the names of the 5 most popular dessert dishes as a " +
                    "comma-delimited list. Please respond with only the comma-delimited list and do not number the dishes.").split(",")
            var allDesserts: MutableList<Recipe> = mutableListOf()
            desserts.forEach { x->
                val response = recipeAndImageHomeScreenApi.getResponse(x)
                if (response.numRecipes > 0) {
                    val tempDesserts: List<RecipeAndImageRecipe> = response.recipes
                    tempDesserts.forEach { y ->
                        allDesserts.add(y.toRecipe())
                        tempList.add(y.toRecipe())
                        homeScreenState = homeScreenState.copy(
                            dessertRecipes = allDesserts,
                            largeList = tempList
                        )
                    }
                }
            }
            tempList.removeAt(0)
            tempList.shuffle()


            homeScreenState = homeScreenState.copy(
                largeList = tempList
            )
        }
    }
*/

    fun updateSearchFlag(flag: Boolean){
        homeScreenState = homeScreenState.copy(
            searchFlag = flag
        )
    }

    fun updateSearchPhrase(phrase: String){
        homeScreenState = homeScreenState.copy(
            searchPhrase = phrase
        )
    }

    fun searchForRecipes(){
        viewModelScope.launch {
            var allSearch: MutableList<Recipe> = mutableListOf()
            val response = recipeAndImageHomeScreenApi.getResponse(homeScreenState.searchPhrase)
            if (response.numRecipes > 0) {
                val tempSearch: List<RecipeAndImageRecipe> = response.recipes
                tempSearch.forEach { x->
                    allSearch.add(x.toRecipe())
                    homeScreenState = homeScreenState.copy(
                        searchRecipes = allSearch
                    )
                }
            }
        }
    }

    companion object{
        private var INSTANCE: HomeScreenVM? = null

        fun getInstance(): HomeScreenVM{
            val vm = INSTANCE ?: synchronized(this){
                HomeScreenVM(App.getApp().container.recipeAndImageApiHomeScreen).also{
                    INSTANCE = it
                }
            }
            return vm
        }
    }

}