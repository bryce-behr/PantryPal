package com.example.pantrypal.viewmodels

import androidx.lifecycle.ViewModel
import com.example.pantrypal.App
import com.example.pantrypal.apis.RecipeAndImageApi

class RecipeAndImageVM(
    private val recipeAndImageApi: RecipeAndImageApi
): ViewModel(){

    /**
     * Implements singleton
     */
    companion object{
        private var INSTANCE: RecipeAndImageVM? = null

        fun getInstance(): RecipeAndImageVM{
            val vm = INSTANCE?: synchronized(this){
                RecipeAndImageVM(App.getApp().container.recipeAndImageApi).also{
                    INSTANCE = it
                }
            }
            return vm
        }
    }

}