package com.example.pantrypal.Screens

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    val recipeList = arrayListOf<Int>()
    for (i in 1..10){
        recipeList.add(0)
    }

    Column (modifier = modifier
        .fillMaxSize()
        .verticalScroll(ScrollState(0), true)){
        for (i in 1..10) {
            RecipeCard(image = 0, description = "test")
        }
    }
    
}

