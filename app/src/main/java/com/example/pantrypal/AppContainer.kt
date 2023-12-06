package com.example.pantrypal

import android.content.Context
import androidx.room.Room
import com.example.pantrypal.apis.OpenAIApi
import com.example.pantrypal.apis.RecipeAndImageApi
import com.example.pantrypal.apis.StableDiffusionApi
import com.example.pantrypal.database.PantryPalDAO
import com.example.pantrypal.database.PantryPalDatabase

interface AppContainer{
    val pantryPalDAO: PantryPalDAO
    val openAIApi: OpenAIApi
    val stableDiffusionApi: StableDiffusionApi
    val recipeAndImageApi: RecipeAndImageApi
}

class DefaultContainer(context: Context): AppContainer{
    private val db by lazy{
        Room.databaseBuilder(
            context,
            PantryPalDatabase::class.java,
            "pantrypaldatabase.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    override val pantryPalDAO: PantryPalDAO by lazy{
        db.pantryPalDAO
    }

    override val openAIApi: OpenAIApi by lazy{
        OpenAIApi
    }

    override val stableDiffusionApi: StableDiffusionApi by lazy{
        StableDiffusionApi
    }

    override val recipeAndImageApi: RecipeAndImageApi by lazy{
        RecipeAndImageApi
    }
}