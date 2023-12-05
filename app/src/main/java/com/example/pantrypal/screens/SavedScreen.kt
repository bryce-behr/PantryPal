package com.example.pantrypal.screens

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.pantrypal.R

@Composable
fun SavedScreen(modifier: Modifier = Modifier) {

    val recipeList = arrayListOf<Int>()
    for (i in 1..10){
        recipeList.add(0)
    }

    Column (modifier = modifier
        .fillMaxSize()
        .verticalScroll(ScrollState(0), true)
    ) {
        Spacer(modifier = Modifier
            .height(75.dp)
            .fillMaxWidth()
        )

        TextField(value = "search", onValueChange = {
            //billAmount = it // it is the new string input by the user
        },
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            //label = { Text("Bill Amount") },
            singleLine = true,
            leadingIcon = { Icon(painter = painterResource(id = R.drawable.search), contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done)
        )

        for (i in 1..10) {
            RecipeCard(image = 0, description ="Indulge in Chicken Alfredo perfection:\n juicy seasoned chicken, al dente fettuccine, and rich Alfredo sauce—a symphony of decadent flavors!")
        }
    }

//    LazyColumn(modifier = modifier.fillMaxSize()){
//        for (recipe in recipeList) {
//            RecipeCard(image = 0, description = "test")
//        }
//    }

}