package com.example.pantrypal.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pantrypal.R
import com.example.pantrypal.database.Recipe
import com.example.pantrypal.viewmodels.RecipeScreenVM

@Composable
fun RecipeScreen(recipeVM: RecipeScreenVM, modifier: Modifier = Modifier) {
    Column(modifier = modifier
        .fillMaxSize()) {
        Spacer(modifier = Modifier
            .height(75.dp)
            .fillMaxWidth()
        )
        Image(painter = painterResource(id = R.drawable.recipe_test_image/*TODO: get the actual image*/), contentDescription = null, contentScale = ContentScale.FillWidth, modifier = modifier.fillMaxWidth())
        Text("Ingredients:", modifier = modifier, fontSize = 20.sp, fontWeight = FontWeight.Bold , textDecoration = TextDecoration.Underline)
        Text(text = recipeVM.recipe.ingredients)

        Text("Instructions:", modifier = modifier, fontSize = 20.sp, fontWeight = FontWeight.Bold , textDecoration = TextDecoration.Underline)
        Text(text = recipeVM.recipe.instructions)
    }
}

