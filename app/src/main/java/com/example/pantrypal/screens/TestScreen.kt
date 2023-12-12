package com.example.pantrypal.screens

import androidx.compose.foundation.layout.Arrangement
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pantrypal.viewmodels.DatabaseVM
import com.example.pantrypal.viewmodels.OpenAIApiVM
import com.example.pantrypal.viewmodels.RecipeAndImageVM
import com.example.pantrypal.viewmodels.StableDiffusionState
import com.example.pantrypal.viewmodels.StableDiffusionVM

@Composable
fun TestScreen(){

    val openAIApiVM: OpenAIApiVM = OpenAIApiVM.getInstance()
    val openAIApiState = openAIApiVM.openAIApiState
    val stableDiffusionVM: StableDiffusionVM = StableDiffusionVM.getInstance()
    val stableDiffusionState = stableDiffusionVM.stableDiffusionState
    val recipeAndImageVM: RecipeAndImageVM = RecipeAndImageVM.getInstance()
    val recipeAndImageState = recipeAndImageVM.recipeAndImageState
    val databaseVM: DatabaseVM = DatabaseVM.getInstance()

    var startGenerate : Boolean by rememberSaveable {
        mutableStateOf(false)
    }

    var recipe = ""
    var image = ""

    openAIApiVM.updateRecipePrompt("chicken, sour cream, cheese, rice - suitable for dinner")
    stableDiffusionVM.updateDrawText("chicken quesadillas")


    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly){

        Button(onClick = {
            stableDiffusionVM.getRecipeImage()

            startGenerate = true
        }) {
            Text(text = "Click me")
        }

        if(startGenerate){
            when(stableDiffusionState){
                is StableDiffusionState.Success -> {
                    AsyncImage( model = ImageRequest.Builder(context = LocalContext.current)
                        // .data(book.volumeInfo.imageLinks?.thumbnail)
                        .data(stableDiffusionState.imageUrl)
                        .crossfade(true)
                        .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .aspectRatio(.8f)
                            .fillMaxWidth())
                }
                is StableDiffusionState.Loading -> {
                    Text("Preparing Image ", fontSize = 30.sp)
                }
                is StableDiffusionState.Error -> {
                    Text("Service Error", fontSize = 30.sp)
                }
            }

//            when(recipeAndImageState){
//                is RecipeAndImageState.Success -> {
//                    Text(text = recipeAndImageState.recipe.recipes[0].Title)
//                    Text(text = recipeAndImageState.recipe.recipes[0].toRecipe().toDisplayString() )
//
//                }
//                is RecipeAndImageState.Loading -> {
//                    Text("preparing title image ", fontSize = 30.sp)
//                }
//                is RecipeAndImageState.Error -> {
//                    Text("Service Error", fontSize = 30.sp)
//                }
//            }
        }
        
    }

}