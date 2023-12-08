package com.example.pantrypal

import com.example.pantrypal.apis.OpenAIApi
import com.example.pantrypal.apis.RecipeAndImageApi
import com.example.pantrypal.apis.StableDiffusionApi

interface AppContainer{
    val openAIApi: OpenAIApi
    val stableDiffusionApi: StableDiffusionApi
    val recipeAndimageApi: RecipeAndImageApi
}

class DefaultAppContainer: AppContainer{

    override val openAIApi: OpenAIApi by lazy{
        OpenAIApi
    }

    override val stableDiffusionApi: StableDiffusionApi by lazy{
        StableDiffusionApi
    }

    override val recipeAndimageApi: RecipeAndImageApi by lazy{
        RecipeAndImageApi
    }
}