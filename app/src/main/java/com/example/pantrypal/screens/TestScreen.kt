package com.example.pantrypal.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pantrypal.viewmodels.OpenAIApiState
import com.example.pantrypal.viewmodels.OpenAIApiVM
import com.example.pantrypal.viewmodels.RecipeAndImageState
import com.example.pantrypal.viewmodels.RecipeAndImageVM
import com.example.pantrypal.viewmodels.StableDiffusionState
import com.example.pantrypal.viewmodels.StableDiffusionVM

@Composable
fun TestScreen(){

//    val openAIApiVM: OpenAIApiVM = viewModel()
//    val openAIApiState = openAIApiVM.openAIApiState
//    val stableDiffusionVM: StableDiffusionVM = viewModel()
//    val stableDiffusionState = stableDiffusionVM.stableDiffusionState
    val recipeAndImageVM: RecipeAndImageVM = RecipeAndImageVM.getInstance()
    val recipeAndImageState = recipeAndImageVM.recipeAndImageState

    var startGenerate : Boolean by rememberSaveable {
        mutableStateOf(false)
    }

    var recipe = ""
    var image = ""

    recipeAndImageVM.updateRecipeText("steak")
    //stableDiffusionVM.updateDrawText("Photo-realistic photo of chicken quesadillas")


    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly){
        
        Button(onClick = {
            recipeAndImageVM.getRecipeAndImage()
            startGenerate = true;
        }) {
            Text(text = "Click me")
        }

        if(startGenerate){
            when(recipeAndImageState){
                is RecipeAndImageState.Success -> {
                    AsyncImage( model = ImageRequest.Builder(context = LocalContext.current)
                        // .data(book.volumeInfo.imageLinks?.thumbnail)
                        .data("https:" + recipeAndImageState.recipe.recipes[0].Image)
                        .crossfade(true)
                        .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .aspectRatio(.8f)
                            .fillMaxWidth())
                }
                is RecipeAndImageState.Loading -> {
                    Text("Preparing Image ", fontSize = 30.sp)
                }
                is RecipeAndImageState.Error -> {
                    Text("Service Error", fontSize = 30.sp)
                }
            }

            when(recipeAndImageState){
                is RecipeAndImageState.Success -> {
                    val ing = recipeAndImageState.recipe.recipes[0].Ingredients
                    Text(text = recipeAndImageState.recipe.recipes[0].Title + "\n" + ing)
                }
                is RecipeAndImageState.Loading -> {
                    Text("preparing title image ", fontSize = 30.sp)
                }
                is RecipeAndImageState.Error -> {
                    Text("Service Error", fontSize = 30.sp)
                }
            }
        }
        
    }

}