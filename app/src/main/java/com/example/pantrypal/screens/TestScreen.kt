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
import com.example.pantrypal.viewmodels.StableDiffusionVM

@Composable
fun TestScreen(){

    val openAIApiVM: OpenAIApiVM = viewModel()
    val openAIApiState = openAIApiVM.openAIApiState
//    val stableDiffusionVM: StableDiffusionVM = viewModel()
//    val stableDiffusionState = stableDiffusionVM.stableDiffusionState

    var startGenerate : Boolean by rememberSaveable {
        mutableStateOf(false)
    }

    var recipe = ""
    var image = ""

    openAIApiVM.updateRecipePrompt("A good title for quesadillas in a cook book")
//    stableDiffusionVM.updateDrawText("")


    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly){
        
        Button(onClick = {
            openAIApiVM.getRecipe()
            startGenerate = true;
        }) {
            Text(text = "Click me")
        }

        if(startGenerate){
            when(openAIApiState){
                is OpenAIApiState.Success -> {
                    Text(text = openAIApiState.chatGPTResponse, fontSize = 30.sp)
                }
                is OpenAIApiState.Loading -> {
                    Text("Preparing Image ", fontSize = 30.sp)
                }
                is OpenAIApiState.Error -> {
                    Text("Service Error", fontSize = 30.sp)
                }
            }
        }
        
    }

}