package com.example.pantrypal.viewmodels

import androidx.lifecycle.ViewModel
import com.example.pantrypal.App
import com.example.pantrypal.apis.OpenAIApi

class OpenAIApiVM(
    private val openAIApi: OpenAIApi
): ViewModel(){

    /**
     * Implements singleton
     */
    companion object{
        private var INSTANCE: OpenAIApiVM? = null

        fun getInstance(): OpenAIApiVM {
            val vm = INSTANCE ?: synchronized(this){
                OpenAIApiVM(App.getApp().container.openAIApi).also {
                    INSTANCE = it
                }
            }
            return vm
        }

    }

}