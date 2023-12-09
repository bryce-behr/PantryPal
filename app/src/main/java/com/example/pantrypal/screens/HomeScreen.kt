package com.example.pantrypal.screens

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pantrypal.R
import com.example.pantrypal.database.Recipe
import com.example.pantrypal.viewmodels.HomeScreenState
import com.example.pantrypal.viewmodels.HomeScreenVM
import com.example.pantrypal.viewmodels.OpenAIApiState
import com.example.pantrypal.viewmodels.OpenAIApiVM
import com.example.pantrypal.viewmodels.RecipeScreenVM
import com.example.pantrypal.viewmodels.StableDiffusionState
import com.example.pantrypal.viewmodels.StableDiffusionVM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, recipeVM: RecipeScreenVM, navController: NavController) {
    var searchText by remember { mutableStateOf("") }

    val vm: HomeScreenVM = HomeScreenVM.getInstance()
    val vmState: HomeScreenState = vm.homeScreenState
    
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

        TextField(value = searchText, onValueChange = {
            searchText = it
        },
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            //label = { Text("Bill Amount") },
            singleLine = true,
            leadingIcon = { Icon(painter = painterResource(id = R.drawable.search), contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search),
            placeholder = { Text("search") },
            keyboardActions = KeyboardActions(onSearch = { this.defaultKeyboardAction(ImeAction.Done) } )
        )

        vmState.largeList.forEach { x ->
            RecipeCard(x, recipeVM = recipeVM, navController = navController)
        }

//        for (i in 1..10) {
//            RecipeCard(Recipe(0, 0, "test meal", "test ingredients", "test instructions", ""), recipeVM = recipeVM, navController = navController/*image = "0", description = "test"*/)
//        }
    }
}

