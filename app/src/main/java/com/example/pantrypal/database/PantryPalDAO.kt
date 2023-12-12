package com.example.pantrypal.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface PantryPalDAO{
    @Insert
    suspend fun insertRecipe(recipe: Recipe)

    @Upsert
    suspend fun upsertRecipe(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    @Query("DELETE FROM recipe WHERE recipeAndImageID = :id")
    suspend fun deleteRecipe(id: Int)

    @Query("DELETE FROM recipe WHERE title = :text")
    suspend fun deleteRecipe(text: String)

    @Query("SELECT title FROM recipe")
    fun getAllTitles(): Flow<List<String>>

    @Query("SELECT * FROM recipe")
    fun getAllRecipes(): Flow<List<Recipe>>

    @Query("SELECT DISTINCT(recipeAndImageID) FROM recipe")
    fun getALLRecipeAndImageIDs(): Flow<List<Int>>

    @Query("SELECT * FROM recipe WHERE recipe_id = :id")
    fun getRecipesWithID(id: Int): Flow<List<Recipe>>

    @Query("SELECT * FROM recipe WHERE recipeAndImageid = :apiID")
    fun getRecipesWithApiID(apiID: Int): Flow<List<Recipe>>

    @Query("SELECT * FROM recipe WHERE title = :name")
    fun getRecipesWithName(name: String): Flow<List<Recipe>>
}