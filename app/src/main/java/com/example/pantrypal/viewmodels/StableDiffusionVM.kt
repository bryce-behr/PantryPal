package com.example.pantrypal.viewmodels

import androidx.lifecycle.ViewModel
import com.example.pantrypal.App
import com.example.pantrypal.apis.StableDiffusionApi

class StableDiffusionVM(
    private val stableDiffusionApi: StableDiffusionApi
): ViewModel(){

    /**
     * Implements singleton
     */
    companion object{
        private var INSTANCE: StableDiffusionVM? = null
        fun getInstance(): StableDiffusionVM{
            val vm = INSTANCE?: synchronized(this){
                StableDiffusionVM(App.getApp().container.stableDiffusionApi).also{
                    INSTANCE = it
                }
            }
            return vm
        }
    }

}