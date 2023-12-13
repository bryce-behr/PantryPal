package com.example.pantrypal.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface PantryPalDAO{
    @Upsert
    suspend fun upsertRecipe(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    @Query("DELETE FROM recipe WHERE recipeAndImageID = :id")
    suspend fun deleteRecipe(id: Int)

    @Query("DELETE FROM recipe WHERE title = :text")
    suspend fun deleteRecipe(text: String)

    @Query("SELECT DISTINCT * FROM recipe WHERE recipeAndImageID LIKE :text OR title LIKE :text OR ingredients LIKE :text OR instructions LIKE :text")
    suspend fun searchRecipes(text: String): List<Recipe>

    @Query("SELECT title FROM recipe")
    fun getAllTitles(): Flow<List<String>>

    @Query("SELECT * FROM recipe")
    fun getAllRecipes(): Flow<List<Recipe>>

    @Query("SELECT DISTINCT(recipeAndImageID) FROM recipe")
    fun getALLRecipeAndImageIDs(): Flow<List<Int>>


}