package com.example.pantrypal.models

import com.example.pantrypal.database.Recipe
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//@Serializable
//data class RecipeRequest(
//    @SerialName(value = "q")
//    val recipeApiRequest: String
//)


@Serializable
data class RecipeAndImageResponse(
    @SerialName(value = "s")
    val success: Int,
    @SerialName(value = "d")
    val recipes: List<RecipeAndImageRecipe>,
    @SerialName(value = "t")
    val numRecipes: Int,
    @SerialName(value = "p")
    val information: Information
)

@Serializable
data class Information(
    val limitstart: Int,
    val limit: Int,
    val total: Int,
    val pagesStart: Int,
    val pagesStop: Int,
    val pagesCurrent: Int,
    val pagesTotal: Int
)

@Serializable
data class RecipeAndImageRecipe(
    val id: Int,
    val Title: String,
    val Ingredients: Map<String, String>,
    val Instructions: String,
    val Image: String
){
    fun toRecipe(): Recipe{

        var listIngredients: MutableList<String> = mutableListOf()

        this.Ingredients.forEach {
            listIngredients.add(it.value)
        }

        var ingredients: String = ""
        for (i in 0 until listIngredients.size){
            ingredients += ((i+1).toString() + ": " + listIngredients[i] + "\n")

        }


        val out = Recipe(
            recipeAndImageID = this.id,
            title = this.Title,
            ingredients = ingredients,
            instructions = this.Instructions,
            image = "https:" + this.Image
        )
        return out
    }
}
