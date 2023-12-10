package com.example.pantrypal.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pantrypal.R
import com.example.pantrypal.viewmodels.RecipeState
import com.example.pantrypal.viewmodels.RecipeVM

@Composable
fun RecipeScreen(modifier: Modifier = Modifier) {

    val recipeVM: RecipeVM = RecipeVM.getInstance()
    val recipeState: RecipeState = recipeVM.recipeState

    Column(modifier = modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())) {
        Spacer(modifier = Modifier
            .height(75.dp)
            .fillMaxWidth()
        )

        when (recipeState){
            is RecipeState.Success -> {
                AsyncImage( model = ImageRequest.Builder(context = LocalContext.current)
                    // .data(book.volumeInfo.imageLinks?.thumbnail)
                    .data(recipeState.recipe.image)
                    .crossfade(true)
                    .build(),
                    contentDescription = recipeState.recipe.title,
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentScale = ContentScale.FillWidth)
                Text("Ingredients:", modifier = modifier, fontSize = 20.sp, fontWeight = FontWeight.Bold , textDecoration = TextDecoration.Underline)
                Text(text = recipeState.recipe.ingredients)

                Text("Instructions:", modifier = modifier, fontSize = 20.sp, fontWeight = FontWeight.Bold , textDecoration = TextDecoration.Underline)
                Text(text = recipeState.recipe.instructions)
            }
            is RecipeState.LoadingSuccess -> {
                Image(painter = painterResource(id = R.drawable.loading), contentDescription = null)
                Text("Ingredients:", modifier = modifier, fontSize = 20.sp, fontWeight = FontWeight.Bold , textDecoration = TextDecoration.Underline)
                Text(text = recipeState.recipe.ingredients)

                Text("Instructions:", modifier = modifier, fontSize = 20.sp, fontWeight = FontWeight.Bold , textDecoration = TextDecoration.Underline)
                Text(text = recipeState.recipe.instructions)
            }
            is RecipeState.HalfSuccess -> {
                Text("Ingredients:", modifier = modifier, fontSize = 20.sp, fontWeight = FontWeight.Bold , textDecoration = TextDecoration.Underline)
                Text(text = recipeState.recipe.ingredients)

                Text("Instructions:", modifier = modifier, fontSize = 20.sp, fontWeight = FontWeight.Bold , textDecoration = TextDecoration.Underline)
                Text(text = recipeState.recipe.instructions)
            }
            is RecipeState.Loading -> {
                Text(text = "Carefully crafting your recipe...")
            }
            is RecipeState.Error -> {
                Text(text = "Error crafting recipe, please try again")
            }
        }
    }
}

