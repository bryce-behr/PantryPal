package com.example.pantrypal

import android.app.Application

class App : Application() {
    lateinit var container: AppContainer

    companion object{

        private var appInstance : App? = null
        fun getApp() : App{
            if(appInstance == null){
                throw Exception("appInstance is null!")
            }
            return appInstance!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
        appInstance = this
    }
}