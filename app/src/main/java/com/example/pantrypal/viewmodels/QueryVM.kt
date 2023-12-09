package com.example.pantrypal.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.pantrypal.App
import com.example.pantrypal.database.Recipe

class QueryVM(

) : ViewModel() {
    var ingredients = mutableListOf("")
    var meal = "Dinner"

    var showGenerated: Boolean by mutableStateOf(false)
        private set

    init {
        removeIngredient("")
        meal = "Dinner"
    }

    fun changeAlertValue() {
        this.showGenerated = !showGenerated
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

    companion object{
        private var INSTANCE: QueryVM? = null

        fun getInstance(): QueryVM{
            val vm = INSTANCE ?: synchronized(this){
                QueryVM().also{
                    INSTANCE = it
                }
            }
            return vm
        }
    }
}