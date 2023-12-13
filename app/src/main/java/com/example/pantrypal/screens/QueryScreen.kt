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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.layout.ContentScale
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pantrypal.NavScreens
import com.example.pantrypal.R
import com.example.pantrypal.deviceSize
import com.example.pantrypal.viewmodels.QueryVM
import com.example.pantrypal.viewmodels.RecipeState
import com.example.pantrypal.viewmodels.RecipeVM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Query(modifier: Modifier = Modifier, navController: NavController) {

    val recipeVM: RecipeVM = RecipeVM.getInstance()
    val vm: QueryVM = QueryVM.getInstance()

    var update by rememberSaveable { mutableStateOf(true) }
    var meal by rememberSaveable { mutableStateOf("dinner") }

    val configuration = LocalConfiguration.current
    deviceSize.screenWidth = configuration.screenWidthDp.dp.value
    deviceSize.screenHeight = configuration.screenHeightDp.dp.value

    Column(modifier = modifier.padding(horizontal = 45.dp, vertical = 20.dp)){

        Spacer(modifier = modifier.height(75.dp))

        Box(modifier = modifier
            .fillMaxWidth()
            .height((deviceSize.screenHeight!! / 2.5).dp)
            .border(15.dp, MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(15))
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

                for (i in 0 until vm.ingredients.size){
                    Ingredient(modifier = modifier
                        .fillMaxWidth()
                        .padding(5.dp), name = vm.ingredients[i])
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
                recipeVM.addIngredients(vm.ingredients)
                recipeVM.addMeal(meal)
                recipeVM.getRecipe()

                vm.changeAlertValue()
            },
                shape = RoundedCornerShape(25),
                modifier = modifier.fillMaxSize()) {
                Text(text = "Generate", fontSize = 20.sp, textAlign = TextAlign.Center)
            }
        }

        if(vm.showGenerated) {
            RecipePopUp(navController = navController)
        }
    }
}

@Composable
fun Ingredient(modifier: Modifier = Modifier, name: String) {

    val vm: QueryVM = QueryVM.getInstance()

    Row(modifier = modifier
        .fillMaxWidth()
        .height(25.dp)
        , horizontalArrangement = Arrangement.SpaceBetween) {
            Text("\t\t\t\t- $name", fontSize = 20.sp)
            IconButton(modifier = Modifier.padding(end = 50.dp), onClick = {
                vm.ingredients.remove(name)
            }) {
                Icon(
                    modifier = Modifier.fillMaxHeight(),
                    painter = painterResource(id = R.drawable.remove),
                    contentDescription = null,
                    tint = Color.Red
                )
            }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenuBox(): String {

    val vm: QueryVM = QueryVM.getInstance()
    LocalContext.current
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
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val recipeVM: RecipeVM = RecipeVM.getInstance()
    val recipeState: RecipeState = recipeVM.recipeState
    val vm: QueryVM = QueryVM.getInstance()

    var viewEnabled by rememberSaveable { mutableStateOf(false) }

    when (recipeState){
        is RecipeState.Success -> {
            viewEnabled = true
        }
        is RecipeState.LoadingSuccess -> {
            viewEnabled = true
        }
        is RecipeState.HalfSuccess -> {
            viewEnabled = true
        }
        is RecipeState.Loading -> {
            viewEnabled = false
        }
        is RecipeState.Error -> {
            viewEnabled = false
        }
    }

    AlertDialog(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        titleContentColor = Color.White,
        onDismissRequest = {
            // Dismiss the dialog when the user clicks outside the dialog or on the back
            // button. If you want to disable that functionality, simply use an empty
            // onCloseRequest.
        },
        icon = {
            when (recipeState){
                is RecipeState.Success -> {
                    AsyncImage( model = ImageRequest.Builder(context = LocalContext.current)
                        // .data(book.volumeInfo.imageLinks?.thumbnail)
                        .data(recipeState.recipe.image)
                        .crossfade(true)
                        .build(),
                        contentDescription = null,
                        modifier = Modifier.height(150.dp),
                        contentScale = ContentScale.FillBounds)
                }
                is RecipeState.LoadingSuccess -> {
                    Image(painter = painterResource(id = R.drawable.loading), contentDescription = null)
                }
                is RecipeState.HalfSuccess -> {
                    Image(painter = painterResource(id = R.drawable.error), contentDescription = null)
                }
                is RecipeState.Loading -> {
                    Image(painter = painterResource(id = R.drawable.loading), contentDescription = null)
                }
                is RecipeState.Error -> {
                    Image(painter = painterResource(id = R.drawable.error), contentDescription = null)
                }
            }
               },
        title = {
            when (recipeState){
                is RecipeState.Success -> {
                    Text(text = recipeState.recipe.title)
                }
                is RecipeState.LoadingSuccess -> {
                    Text(text = recipeState.recipe.title)
                }
                is RecipeState.HalfSuccess -> {
                    Text(text = recipeState.recipe.title)
                }
                is RecipeState.Loading -> {
                    Text(text = "Carefully crafting your recipe...")
                }
                is RecipeState.Error -> {
                    Text(text = "Error crafting recipe, please try again")
                }
            }
                },
        modifier = modifier,
        dismissButton = {
            Button(
                onClick = {
                    recipeVM.clearRecipeQuery()
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
                navController.navigate(NavScreens.Recipe.route)
                vm.changeAlertValue()
                },
                modifier = modifier
                    .padding(end = 30.dp),
                enabled = viewEnabled) {
                Text(text = "View")
            }
        }
    )
}

