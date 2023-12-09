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
    var search: Boolean = false,
    var breakfastFlag: Boolean = true,
    var lunchFlag: Boolean = true,
    var dinnerFlag: Boolean = true,
    var dessertFlag: Boolean = true
){}

class HomeScreenVM(
    private val recipeAndImageHomeScreenApi: RecipeAndImageApi
): ViewModel(){

    var homeScreenState: HomeScreenState by mutableStateOf(HomeScreenState())

    init{
        println("Before")
        viewModelScope.launch {
            println("1")
            val breakfasts: List<String> = OpenAIApi.getResponse("please give me the names of the 5 most popular breakfast dishes as a " +
                    "comma-delimited list. Please respond with only the comma-delimited list and do not number the dishes.").split(",")
            println("2")
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
            println("6")
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
            println("7")
            println("3")
            val dinners: List<String> = OpenAIApi.getResponse("please give me the names of the 5 most popular dinner dishes as a " +
                    "comma-delimited list. Please respond with only the comma-delimited list and do not number the dishes.").split(",")
            println("4")
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
            println("8")
            val desserts: List<String> = OpenAIApi.getResponse("please give me the names of the 5 most popular dessert dishes as a " +
                    "comma-delimited list. Please respond with only the comma-delimited list and do not number the dishes.").split(",")
            println("5")
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
//            println("9")
//
//            var out: MutableList<Recipe> = mutableListOf()
//
//            if (homeScreenState.breakfastFlag){
//                out.addAll(allBreakfasts)
//            }
//
//            if (homeScreenState.lunchFlag){
//                out.addAll(allLunches)
//            }
//
//            if (homeScreenState.dinnerFlag){
//                out.addAll(allDinners)
//            }
//
//            if (homeScreenState.dessertFlag){
//                out.addAll(allDesserts)
//            }

            tempList.shuffle()

            println("10")

            homeScreenState = homeScreenState.copy(
                largeList = tempList
            )
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