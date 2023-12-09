package com.example.pantrypal.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    var recipe_id: Int = 0,
    val recipeAndImageID: Int = 0,
    val title: String,
    val ingredients: String,
    val instructions: String,
    val image: String
){
    fun toDisplayString(): String{
        return ("Title: " + this.title +"" +
                "\nIngredients:\n" + this.ingredients + "" +
                "\nInstructions: " + this.instructions)
    }
}