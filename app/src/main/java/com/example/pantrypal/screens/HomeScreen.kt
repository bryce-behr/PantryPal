package com.example.pantrypal.screens

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pantrypal.App
import com.example.pantrypal.R
import com.example.pantrypal.viewmodels.OpenAIApiState
import com.example.pantrypal.viewmodels.OpenAIApiVM
import com.example.pantrypal.viewmodels.StableDiffusionState
import com.example.pantrypal.viewmodels.StableDiffusionVM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    /**
     * For testing purposes
     */
    //val app : App = App()

    val openAIApiVM: OpenAIApiVM = OpenAIApiVM.getInstance()
    val openAIApiState = openAIApiVM.openAIApiState
    val stableDiffusionVM: StableDiffusionVM = StableDiffusionVM.getInstance()
    val stableDiffusionState = stableDiffusionVM.stableDiffusionState
    var startGenerate: Boolean by rememberSaveable{
        mutableStateOf(false)
    }
    var imageUrl: String = ""
    var recipeTest: String = ""
    
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

        TextField(value = "search", onValueChange = {
            //billAmount = it // it is the new string input by the user
        },
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            //label = { Text("Bill Amount") },
            singleLine = true,
            leadingIcon = { Icon(painter = painterResource(id = R.drawable.search), contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done)
        )
        
        Button(onClick = {
            openAIApiVM.updateRecipePrompt("write a good title for quesadillas for a cook book")
            stableDiffusionVM.updateDrawText("quesadilla")
            startGenerate = true
        }) {
            Text(text = "Click Me")
        }

        if(startGenerate){
            when(openAIApiState){
                is OpenAIApiState.Success -> {
//                    AsyncImage( model = ImageRequest.Builder(context = LocalContext.current)
//                        // .data(book.volumeInfo.imageLinks?.thumbnail)
//                        .data(stUIState.imageUrl)
//                        .crossfade(true)
//                        .build(),
//                        contentDescription = null,
//                        contentScale = ContentScale.FillBounds,
//                        modifier = Modifier
//                            .aspectRatio(.8f)
//                            .fillMaxWidth())
                    openAIApiVM.getRecipe()
                    recipeTest = openAIApiState.chatGPTResponse
                }
                is OpenAIApiState.Loading -> {
                    //Text("Preparing Recipe ", fontSize = 30.sp)
                }
                is OpenAIApiState.Error -> {
                    //Text("Service Error", fontSize = 30.sp)
                }
            }

            when(stableDiffusionState){
                is StableDiffusionState.Success -> {
//                    AsyncImage( model = ImageRequest.Builder(context = LocalContext.current)
//                        // .data(book.volumeInfo.imageLinks?.thumbnail)
//                        .data(stUIState.imageUrl)
//                        .crossfade(true)
//                        .build(),
//                        contentDescription = null,
//                        contentScale = ContentScale.FillBounds,
//                        modifier = Modifier
//                            .aspectRatio(.8f)
//                            .fillMaxWidth())
                    stableDiffusionVM.getRecipeImage()
                    imageUrl = stableDiffusionState.imageUrl
                }
                is StableDiffusionState.Loading -> {
                    //Text("Preparing Recipe ", fontSize = 30.sp)
                }
                is StableDiffusionState.Error -> {
                    //Text("Service Error", fontSize = 30.sp)
                }
            }
            for (i in 1..10) {
                RecipeCard(image = imageUrl, description = recipeTest)
            }
        } else {
            for (i in 1..10) {
                RecipeCard(image = "0", description = "test")
            }
        }
    }


    
}

