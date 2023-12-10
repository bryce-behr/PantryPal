package com.example.pantrypal.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.RowScopeInstance.align
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pantrypal.NavScreens
import com.example.pantrypal.R
import com.example.pantrypal.database.Recipe
import com.example.pantrypal.deviceSize
import com.example.pantrypal.viewmodels.OpenAIApiState
import com.example.pantrypal.viewmodels.OpenAIApiVM
import com.example.pantrypal.viewmodels.QueryVM
import com.example.pantrypal.viewmodels.RecipeScreenVM
import com.example.pantrypal.viewmodels.StableDiffusionState
import com.example.pantrypal.viewmodels.StableDiffusionVM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Query(modifier: Modifier = Modifier, navController: NavController) {

    val openAIApiVM: OpenAIApiVM = OpenAIApiVM.getInstance()
    val openAIApiState: OpenAIApiState = openAIApiVM.openAIApiState
    val stableDiffusionVM: StableDiffusionVM = StableDiffusionVM.getInstance()
    val stableDiffusionState: StableDiffusionState = stableDiffusionVM.stableDiffusionState
    val recipeVM: RecipeScreenVM = RecipeScreenVM.getInstance()
    val vm: QueryVM = QueryVM.getInstance()

    var update by rememberSaveable { mutableStateOf(true) }
    var meal by rememberSaveable { mutableStateOf("dinner") }

    var chatGPTResponse by rememberSaveable { mutableStateOf("")}
    var stableDiffusionResponse by rememberSaveable { mutableStateOf("")}


    val configuration = LocalConfiguration.current
    deviceSize.screenWidth = configuration.screenWidthDp.dp.value
    deviceSize.screenHeight = configuration.screenHeightDp.dp.value

    println("query screen is recomposing")
    if(vm.showGenerated) {
        println("${recipeVM.recipe.title} + ${recipeVM.recipe.image}")
        RecipePopUp(navController = navController)
    }

    Column(modifier = modifier.padding(horizontal = 45.dp, vertical = 20.dp)){

        Spacer(modifier = modifier.height(75.dp))

        Box(modifier = modifier
            .fillMaxWidth()
            .height((deviceSize.screenHeight!! / 2.5).dp)
            .border(15.dp, Color.hsv(158f, 1f, .2f, 1f), shape = RoundedCornerShape(15))
//            .background(Color.LightGray)
        ){
            Column(modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Spacer(modifier = modifier.height(10.dp))
                Text("Meal:", modifier = modifier.padding(5.dp), fontSize = 20.sp, fontWeight = FontWeight.Bold , textDecoration = TextDecoration.Underline)
                Text("\t${vm.meal}", modifier = modifier.padding(5.dp), fontSize = 20.sp)
                Text("Ingredients:", modifier = modifier.padding(5.dp), fontSize = 20.sp, fontWeight = FontWeight.Bold , textDecoration = TextDecoration.Underline)

//                for(i in 0 until vm.ingredients.size) {
//                    update = !update
//                    Ingredient(modifier = modifier
//                        .fillMaxWidth()
//                        .padding(5.dp), name = vm.ingredients[i])
//                }
                vm.ingredients.forEach { x->
                    Ingredient(modifier = modifier
                        .fillMaxWidth()
                        .padding(5.dp), name = x)
                }
                Spacer(modifier = modifier.height(10.dp))
            }
        }

        Spacer(modifier = modifier.weight(.5f))

        Text("Meal:",
            modifier = modifier.padding(15.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline)

        Row(modifier = Modifier
            .fillMaxWidth()
            .weight(2f)){ meal = (ExposedDropdownMenuBox()) }

        Spacer(modifier = modifier.weight(.5f))

        Text(text = "Ingredients:",
            modifier = modifier.padding(15.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline)

        Row(modifier = Modifier
            .fillMaxWidth()
            .weight(2f),
            horizontalArrangement = Arrangement.SpaceEvenly){
            var ingText by rememberSaveable { mutableStateOf("") }
            TextField(value = ingText, onValueChange = { ingText = it },
                //shape = RoundedCornerShape(corner = CornerSize(25)),
                modifier = modifier
                    .weight(2f)
                    .fillMaxHeight()
                    .padding(end = 16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    if(ingText != "") {
                        vm.ingredients.add(ingText)
                        ingText = ""
                        update = !update
                    }
                    this.defaultKeyboardAction(ImeAction.Done)
                })
            )

            Button(onClick = {
                if(ingText != ""){
                    vm.ingredients.add(ingText)
                    ingText = ""
                    update = !update
                }
            },
            shape = RoundedCornerShape(corner = CornerSize(25)),
            modifier = modifier
                .weight(1f)
                .fillMaxHeight()) {
                Text(text = "Add", fontSize = 20.sp, textAlign = TextAlign.Center)
            }
        }

        Spacer(modifier = modifier.weight(1f))

        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier
            .fillMaxWidth()
            .weight(2f)){
            Button(onClick = {
                var prompt = ""
                vm.ingredients.forEach { x->
                    prompt = prompt + x + ";"
                }
                prompt = prompt.dropLast(1)
                prompt = prompt + " - Make it suited for " + vm.meal
                openAIApiVM.updateRecipePrompt(prompt)
                openAIApiVM.getRecipe()
                vm.changeAlertValue()
            },
                shape = RoundedCornerShape(25),
                modifier = modifier.fillMaxSize()) {
                Text(text = "Generate", fontSize = 20.sp, textAlign = TextAlign.Center)
            }
        }

        /*if (vm.showGenerated){
            when (openAIApiState){
                is OpenAIApiState.Success -> {
                    chatGPTResponse = openAIApiState.chatGPTResponse
                    stableDiffusionVM.updateDrawText(chatGPTResponse)
                    stableDiffusionVM.getRecipeImage()
                    when (stableDiffusionState){
                        is StableDiffusionState.Success ->{
                            var responseList: List<String> = chatGPTResponse.split(";")

                            if (responseList.size == 3) {
                                var titleStr = responseList[0].split("\"")[1]
                                var ingredientsStr = "{" + responseList[1].replace(":", "=") + "}"
                                var instructionsStr = responseList[2].split("\"")[1]

                                val out = Recipe(
                                    title = titleStr,
                                    ingredients = ingredientsStr,
                                    instructions = instructionsStr,
                                    image = stableDiffusionState.imageUrl
                                )
                                recipeVM.ChangeRecipeTo(out)
                            }
                        }
                        is StableDiffusionState.Loading ->{
                            var responseList: List<String> = chatGPTResponse.split(";")

                            if (responseList.size == 3) {
                                var titleStr = responseList[0].split("\"")[1]
                                var ingredientsStr = "{" + responseList[1].replace(":", "=") + "}"
                                var instructionsStr = responseList[2].split("\"")[1]

                                val out = Recipe(
                                    title = titleStr,
                                    ingredients = ingredientsStr,
                                    instructions = instructionsStr,
                                    image = ""
                                )
                                recipeVM.ChangeRecipeTo(out)
                            }
                        }
                        is StableDiffusionState.Error ->{
                            var responseList: List<String> = chatGPTResponse.split(";")

                            if (responseList.size == 3) {
                                var titleStr = responseList[0].split("\"")[1]
                                var ingredientsStr = "{" + responseList[1].replace(":", "=") + "}"
                                var instructionsStr = responseList[2].split("\"")[1]

                                val out = Recipe(
                                    title = titleStr,
                                    ingredients = ingredientsStr,
                                    instructions = instructionsStr,
                                    image = ""
                                )
                                recipeVM.ChangeRecipeTo(out)
                            }
                        }
                    }
                }
                is OpenAIApiState.Loading -> {
                    val out = Recipe(
                        title = "",
                        ingredients = "",
                        instructions = "",
                        image = ""
                    )
                    recipeVM.ChangeRecipeTo(out)
                }
                is OpenAIApiState.Error -> {
                    val out = Recipe(
                        title = "",
                        ingredients = "",
                        instructions = "",
                        image = ""
                    )
                    recipeVM.ChangeRecipeTo(out)
                }
            }
        }*/
    }
}

@Composable
fun Ingredient(modifier: Modifier = Modifier, name: String) {

    val vm: QueryVM = QueryVM.getInstance()

    Row(modifier = modifier
        .fillMaxWidth()
        .height(25.dp)
        , horizontalArrangement = Arrangement.SpaceBetween) {
       // Column (modifier = modifier.background(Color.Red)){
            Text("\t\t\t\t- $name", fontSize = 20.sp)
            IconButton(modifier = Modifier.padding(end = 50.dp)/*.background(Color.Blue)*/, onClick = {
                vm.ingredients.remove(name)
            }) {
                Icon(
                    modifier = Modifier.fillMaxHeight()/*.background(Color.Red)*/,
                    painter = painterResource(id = R.drawable.remove),
                    contentDescription = null,
                    tint = Color.Red
                )
            }
        //  }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenuBox(modifier: Modifier = Modifier) : String {

    val vm: QueryVM = QueryVM.getInstance()

    val context = LocalContext.current
    val meals = arrayOf("Breakfast", "Lunch", "Dinner")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(meals[2]) }

    Box (modifier = Modifier
        .fillMaxWidth()
    ){
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                //shape = RoundedCornerShape(corner = CornerSize(25)),
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .fillMaxSize()
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                meals.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedText = item
                            vm.meal = selectedText
                            expanded = false
                            //Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }

    return selectedText
}

@Composable
private fun RecipePopUp(
    //recipe: Recipe,
    navController: NavController,
    modifier: Modifier = Modifier
) {

    val recipeVM: RecipeScreenVM = RecipeScreenVM.getInstance()
    val vm: QueryVM = QueryVM.getInstance()

    AlertDialog(
        containerColor = Color.hsv(158f, 1f, .2f, 1f),
        titleContentColor = Color.White,
        onDismissRequest = {
            // Dismiss the dialog when the user clicks outside the dialog or on the back
            // button. If you want to disable that functionality, simply use an empty
            // onCloseRequest.
        },
        icon = { Image(painter = /*TODO: make this correct image*/painterResource(id = R.drawable.recipe_test_image), contentDescription = null) },
        title = { Text(text = recipeVM.recipe.title) },
        modifier = modifier,
        dismissButton = {
            Button(
                onClick = {
                    vm.changeAlertValue()
                },
                modifier = modifier
                    .padding(end = 50.dp)
            ) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            Button(onClick = {
                //recipeVM.ChangeRecipeTo(recipe)
                navController.navigate(NavScreens.Recipe.route)
                vm.changeAlertValue()
                },
                modifier = modifier
                    .padding(end = 30.dp)) {
                Text(text = "View")
            }
        }
    )
}

