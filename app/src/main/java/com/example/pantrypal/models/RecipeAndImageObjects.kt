package com.example.pantrypal.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeRequest(
    @SerialName(value = "q")
    val recipeApiRequest: String
)


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
    val Ingredients: Ingredient,
    val Instructions: String,
    val Image: String
)

/**
 * This one might not work
 */
@Serializable
data class Ingredient(
    @SerialName("1")
    val one: String
)