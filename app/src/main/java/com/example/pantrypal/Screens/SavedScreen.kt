package com.example.pantrypal.Screens

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SavedScreen(modifier: Modifier = Modifier) {

    val recipeList = arrayListOf<Int>()
    for (i in 1..10){
        recipeList.add(0)
    }

    Column (modifier = modifier
        .fillMaxSize()
        .verticalScroll(ScrollState(0), true)){
        Spacer(modifier = Modifier.height(75.dp).fillMaxWidth())
        for (i in 1..10) {
            RecipeCard(image = 0, description = "test")
        }
    }

//    LazyColumn(modifier = modifier.fillMaxSize()){
//        for (recipe in recipeList) {
//            RecipeCard(image = 0, description = "test")
//        }
//    }

}