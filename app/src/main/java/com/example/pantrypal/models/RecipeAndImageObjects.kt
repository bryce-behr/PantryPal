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
    val one: String ?,
    @SerialName("2")
    val two: String ?,
//    @SerialName("3")
//    val three: String ?,
//    @SerialName("4")
//    val four: String ?,
//    @SerialName("5")
//    val five: String ?,
//    @SerialName("6")
//    val six: String ?,
//    @SerialName("7")
//    val seven: String ?,
//    @SerialName("8")
//    val eight: String ?,
//    @SerialName("9")
//    val nine: String ?,
//    @SerialName("10")
//    val ten: String ?,
//    @SerialName("11")
//    val eleven: String ?,
//    @SerialName("12")
//    val twelve: String ?,
//    @SerialName("13")
//    val thirteen: String ?,
//    @SerialName("14")
//    val fourteen: String ?,
//    @SerialName("15")
//    val fifteen: String ?,
//    @SerialName("16")
//    val sixteen: String ?,
//    @SerialName("17")
//    val seventeen: String ?,
//    @SerialName("18")
//    val eighteen: String ?,
//    @SerialName("19")
//    val nineteen: String ?,
//    @SerialName("20")
//    val twenty: String ?,
//    @SerialName("21")
//    val twentyOne: String ?,
//    @SerialName("22")
//    val twentyTwo: String ?,
//    @SerialName("23")
//    val twentyThree: String ?,
//    @SerialName("24")
//    val twentyFour: String ?,
//    @SerialName("25")
//    val twentyFive: String ?,
//    @SerialName("26")
//    val twentySix: String ?,
//    @SerialName("27")
//    val twentySeven: String ?,
//    @SerialName("28")
//    val twentyEight: String ?,
//    @SerialName("29")
//    val twentyNine: String ?,
//    @SerialName("30")
//    val thirty: String ?

)