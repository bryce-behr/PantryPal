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

    @Query("SELECT * FROM recipe")
    fun getAllRecipes(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipe WHERE recipe_id = :id")
    fun getRecipesWithID(id: Int): Flow<List<Recipe>>

    @Query("SELECT * FROM recipe WHERE recipeAndImageid = :apiID")
    fun getRecipesWithApiID(apiID: Int): Flow<List<Recipe>>

    @Query("SELECT * FROM recipe WHERE title = :name")
    fun getRecipesWithName(name: String): Flow<List<Recipe>>


}