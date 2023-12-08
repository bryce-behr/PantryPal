package com.example.pantrypal.viewmodels

import androidx.lifecycle.ViewModel

class QueryVM() : ViewModel() {
    var ingredients = mutableListOf("")
    var meal = "Dinner"

    init {
        removeIngredient("")
        meal = "Dinner"
    }

    fun addIngredient(ingredient: String) {
        ingredients.add(ingredient)
    }

    fun removeIngredient(ingredient: String) {
        ingredients.remove(ingredient)
    }

    fun getIngredients() : String{
        return ingredients.toString()
    }
}