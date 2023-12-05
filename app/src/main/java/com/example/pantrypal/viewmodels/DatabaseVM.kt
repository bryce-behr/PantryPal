package com.example.pantrypal.viewmodels

import androidx.lifecycle.ViewModel
import com.example.pantrypal.App
import com.example.pantrypal.database.PantryPalDAO

class DatabaseVM(
    private val pantryPalDAO: PantryPalDAO
): ViewModel(){

    /**
     * Implements singleton
     */
    companion object{
        private var INSTANCE: DatabaseVM? = null

        fun getInstance(): DatabaseVM{
            val vm = INSTANCE?: synchronized(this){
                DatabaseVM(App.getApp().container.pantryPalDAO).also{
                    INSTANCE = it
                }
            }
            return vm
        }
    }
}