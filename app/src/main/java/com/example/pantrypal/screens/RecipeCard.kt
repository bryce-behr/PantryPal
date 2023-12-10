package com.example.pantrypal.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pantrypal.NavScreens
import com.example.pantrypal.R
import com.example.pantrypal.database.Recipe
import com.example.pantrypal.deviceSize
import com.example.pantrypal.viewmodels.RecipeState
import com.example.pantrypal.viewmodels.RecipeVM

@Composable
fun RecipeCard(recipe: Recipe/*image: String, description: String*/, modifier: Modifier = Modifier, navController: NavController) {

    val recipeVM: RecipeVM = RecipeVM.getInstance()

    val configuration = LocalConfiguration.current
    deviceSize.screenWidth = configuration.screenWidthDp.dp.value
    deviceSize.screenHeight = configuration.screenHeightDp.dp.value

    Card(modifier = modifier
        .fillMaxSize()
        .padding(8.dp)
        .requiredWidth(((deviceSize.screenWidth ?: 100f) - 16f).dp)
        .clickable(onClick = {
            recipeVM.loadRecipe(recipe)
            navController.navigate(NavScreens.Recipe.route)
        })
    ){
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(recipe.image)
                .crossfade(true)
                .build(),
            contentDescription = recipe.title,
            modifier = Modifier
                .height(180.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            text = recipe.title,
            maxLines = 1, modifier = modifier.padding(16.dp)
        )
    }
}

