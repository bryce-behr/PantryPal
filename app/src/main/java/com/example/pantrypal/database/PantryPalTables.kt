package com.example.pantrypal.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe")
data class Recipe(
    @PrimaryKey
    var recipe_id: Int = 1,
    val recipeAndImageid: Int = 0,
    val title: String = "",
    val ingredients: String,
    val instructions: String = "",
    val image: String = ""
)