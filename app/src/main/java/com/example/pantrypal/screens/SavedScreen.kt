package com.example.pantrypal.screens

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pantrypal.viewmodels.DatabaseVM

@Composable
fun SavedScreen(navController: NavController, modifier: Modifier = Modifier) {

    val vm: DatabaseVM = DatabaseVM.getInstance()
    val recipeList by vm.recipes.collectAsState()

    Column (modifier = modifier
        .fillMaxSize()
        .verticalScroll(ScrollState(0), true)
    ) {
        Spacer(modifier = Modifier
            .height(75.dp)
            .fillMaxWidth()
        )

        Text("This is the bookmarks page")

//        TextField(value = "search", onValueChange = {
//            //billAmount = it // it is the new string input by the user
//        },
//            modifier = modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            //label = { Text("Bill Amount") },
//            singleLine = true,
//            leadingIcon = { Icon(painter = painterResource(id = R.drawable.search), contentDescription = null) },
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text,
//                imeAction = ImeAction.Done)
//        )
//
//        for (i in 1..10) {
//            RecipeCard(image = "0", description ="Indulge in Chicken Alfredo perfection:\n juicy seasoned chicken, al dente fettuccine, and rich Alfredo sauce—a symphony of decadent flavors!")
//        }
        recipeList.forEach { x ->
            RecipeCard(x, navController = navController)
        }
    }
}