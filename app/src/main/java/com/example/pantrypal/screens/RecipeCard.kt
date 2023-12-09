package com.example.pantrypal.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.pantrypal.viewmodels.OpenAIApiVM
import com.example.pantrypal.viewmodels.RecipeScreenVM
import com.example.pantrypal.viewmodels.StableDiffusionVM

@Composable
fun RecipeCard(recipe: Recipe/*image: String, description: String*/, modifier: Modifier = Modifier, recipeVM: RecipeScreenVM, navController: NavController) {

    val configuration = LocalConfiguration.current
    deviceSize.screenWidth = configuration.screenWidthDp.dp.value
    deviceSize.screenHeight = configuration.screenHeightDp.dp.value

    Card(modifier = modifier
        .fillMaxSize()
        .padding(8.dp)
        .requiredWidth(((deviceSize.screenWidth ?: 100f) - 16f).dp)
        .clickable(onClick = {
            recipeVM.ChangeRecipeTo(recipe)
            navController.navigate(NavScreens.Recipe.route)
        })
    ){

        AsyncImage( model = ImageRequest.Builder(context = LocalContext.current)
            // .data(book.volumeInfo.imageLinks?.thumbnail)
            .data(recipe.image)
            .crossfade(true)
            .build(),
            contentDescription = recipe.title,
            modifier = Modifier
                .height(180.dp),
            contentScale = ContentScale.FillBounds)

//        Image(
//            painter = painterResource(R.drawable.recipe_test_image/*TODO: replace this with image from DB*/),
//            contentDescription = recipe.title,
//            modifier = modifier.height(180.dp),
//            contentScale = ContentScale.FillBounds
//        )

        Text(
            text = recipe.title,
            maxLines = 1, modifier = modifier.padding(16.dp)
        )
    }
}

