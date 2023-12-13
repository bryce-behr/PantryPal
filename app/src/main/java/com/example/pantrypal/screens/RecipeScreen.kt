package com.example.pantrypal.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.sourceInformation
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
                println(recipeState.recipe.ingredients)
                Spacer(modifier = modifier
                    .background(MaterialTheme.colorScheme.outline)
                    .fillMaxWidth()
                    .height(25.dp))
                AsyncImage( model = ImageRequest.Builder(context = LocalContext.current)
                    .data(recipeState.recipe.image)
                    .crossfade(true)
                    .build(),
                    contentDescription = recipeState.recipe.title,
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentScale = ContentScale.FillWidth)
                Text(text = recipeState.recipe.title, fontSize = 30.sp, fontWeight = FontWeight.ExtraBold, color = Color.White, lineHeight = 32.sp, textAlign = TextAlign.Center, modifier = Modifier
                    .background(MaterialTheme.colorScheme.outline)
                    .padding(start = 15.dp, end = 15.dp, top = 3.dp, bottom = 5.dp))
                Spacer(modifier = modifier.height(20.dp))
                Text("Ingredients:", modifier = modifier.padding(horizontal = 15.dp), fontSize = 20.sp, fontWeight = FontWeight.Bold , textDecoration = TextDecoration.Underline)
                Text(text = recipeState.recipe.ingredients, modifier = modifier.padding(horizontal = 15.dp))

                Spacer(modifier = modifier.height(16.dp))
                Text("Instructions:", modifier = modifier.padding(horizontal = 15.dp), fontSize = 20.sp, fontWeight = FontWeight.Bold , textDecoration = TextDecoration.Underline)
                Text(text = recipeState.recipe.instructions, modifier = modifier.padding(horizontal = 15.dp))
            }
            is RecipeState.LoadingSuccess -> {
                println(recipeState.recipe.ingredients)
                Spacer(modifier = modifier
                    .background(MaterialTheme.colorScheme.outline)
                    .fillMaxWidth()
                    .height(25.dp))
                Image(painter = painterResource(id = R.drawable.loading), contentDescription = null)
                Text(text = recipeState.recipe.title, fontSize = 30.sp, fontWeight = FontWeight.ExtraBold, color = Color.White, lineHeight = 32.sp, textAlign = TextAlign.Center, modifier = Modifier
                    .background(MaterialTheme.colorScheme.outline)
                    .padding(start = 15.dp, end = 15.dp, top = 3.dp, bottom = 5.dp))
                Spacer(modifier = modifier.height(20.dp))
                Text("Ingredients:", modifier = modifier.padding(horizontal = 15.dp), fontSize = 20.sp, fontWeight = FontWeight.Bold , textDecoration = TextDecoration.Underline)
                Text(text = recipeState.recipe.ingredients, modifier = modifier.padding(horizontal = 15.dp))

                Spacer(modifier = modifier.height(16.dp))
                Text("Instructions:", modifier = modifier.padding(horizontal = 15.dp), fontSize = 20.sp, fontWeight = FontWeight.Bold , textDecoration = TextDecoration.Underline)
                Text(text = recipeState.recipe.instructions, modifier = modifier.padding(horizontal = 15.dp))
            }
            is RecipeState.HalfSuccess -> {
                println(recipeState.recipe.ingredients)
                Text(text = recipeState.recipe.title, fontSize = 30.sp, fontWeight = FontWeight.ExtraBold, color = Color.White, lineHeight = 32.sp, textAlign = TextAlign.Center, modifier = Modifier
                    .background(MaterialTheme.colorScheme.outline)
                    .padding(start = 15.dp, end = 15.dp, top = 3.dp, bottom = 5.dp))
                Spacer(modifier = modifier.height(20.dp))
                Text("Ingredients:", modifier = modifier.padding(horizontal = 15.dp), fontSize = 20.sp, fontWeight = FontWeight.Bold , textDecoration = TextDecoration.Underline)
                Text(text = recipeState.recipe.ingredients, modifier = modifier.padding(horizontal = 15.dp))

                Spacer(modifier = modifier.height(16.dp))
                Text("Instructions:", modifier = modifier.padding(horizontal = 15.dp), fontSize = 20.sp, fontWeight = FontWeight.Bold , textDecoration = TextDecoration.Underline)
                Text(text = recipeState.recipe.instructions, modifier = modifier.padding(horizontal = 15.dp))
            }
            is RecipeState.Loading -> {
            }
            is RecipeState.Error -> {
            }
        }
        Spacer(modifier = modifier.height(150.dp))
    }
}

