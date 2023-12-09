package com.example.pantrypal.screens

import android.app.Activity
import android.graphics.fonts.FontStyle
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.pantrypal.NavScreens
import com.example.pantrypal.R
import com.example.pantrypal.database.Recipe
import com.example.pantrypal.deviceSize
import com.example.pantrypal.viewmodels.QueryVM
import com.example.pantrypal.viewmodels.RecipeScreenVM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Query(modifier: Modifier = Modifier, vm: QueryVM, navController: NavController, recipeVM: RecipeScreenVM) {

    var update by rememberSaveable { mutableStateOf(true) }
    var meal by rememberSaveable { mutableStateOf(vm.meal) }


    val configuration = LocalConfiguration.current
    deviceSize.screenWidth = configuration.screenWidthDp.dp.value
    deviceSize.screenHeight = configuration.screenHeightDp.dp.value

//    if(update) {
//        Text("")
//    }

    if(vm.showGenerated) {
        GenerateRecipePopUp(recipe = /*TODO: make this the actual recipe response*/Recipe(0,0,"Test Title","","",""), vm = vm, navController = navController, recipeVM = recipeVM)
    }

    Column(modifier = modifier.padding(15.dp)){

        Spacer(modifier = modifier.height(75.dp))

        Box(modifier = modifier
            .fillMaxWidth()
            .height((deviceSize.screenHeight!! / 2.5).dp)
            .border(10.dp, Color.Black)
        ){
            Column(modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 10.dp)
            ){
                Spacer(modifier = modifier.height(10.dp))
                Text("Meal:", modifier = modifier.padding(5.dp), fontSize = 20.sp, fontWeight = FontWeight.Bold , textDecoration = TextDecoration.Underline)
                Text("\t${vm.meal}", modifier = modifier.padding(5.dp), fontSize = 20.sp)
                Text("Ingredients:", modifier = modifier.padding(5.dp), fontSize = 20.sp, fontWeight = FontWeight.Bold , textDecoration = TextDecoration.Underline)
                for(i in 0 until vm.ingredients.size) {
                    update = !update
                    Ingredient(modifier = modifier
                        .fillMaxWidth()
                        .padding(5.dp), name = vm.ingredients[i], vm = vm)
                }
                Spacer(modifier = modifier.height(10.dp))
            }
        }

        Spacer(modifier = modifier.height(30.dp))

        Text("Meal:", modifier = modifier.padding(15.dp))

        Row(modifier = Modifier
            .fillMaxWidth()
            .weight(2f)){ meal = (ExposedDropdownMenuBox(vm = vm)) }

        Spacer(modifier = modifier.weight(1f))

        Text(text = "Ingredients:",
            modifier = modifier.padding(15.dp),
            fontSize = 20.sp)

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
                keyboardActions = KeyboardActions(onAny = {
                    this.defaultKeyboardAction(ImeAction.Done)
                    if(ingText != "") {
                        vm.ingredients.add(ingText)
                        ingText = ""
                        update = !update
                    }
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

        Spacer(modifier = modifier.weight(2f))

        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier
            .fillMaxWidth()
            .weight(2f)){
            Button(onClick = {
                 vm.changeAlertValue()
                //GenerateRecipePopUp(recipe = Recipe(0,0,"","","",""))
            },
                shape = RoundedCornerShape(25),
                modifier = modifier.fillMaxSize()) {
                Text(text = "Generate")
            }
        }
    }
}

@Composable
fun Ingredient(modifier: Modifier = Modifier, name: String, vm: QueryVM) {
    Row(modifier = modifier
        .fillMaxWidth()
        .height(25.dp)
        .background(Color.LightGray)
        , horizontalArrangement = Arrangement.SpaceBetween) {
       // Column (modifier = modifier.background(Color.Red)){
            Text("\t\t\t\t- $name", fontSize = 20.sp)
            IconButton(modifier = Modifier.padding(end = 50.dp)/*.background(Color.Blue)*/, onClick = {
                vm.ingredients.remove(name)
                println(vm.ingredients.toString())
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
fun ExposedDropdownMenuBox(modifier: Modifier = Modifier, vm: QueryVM) : String {
    val context = LocalContext.current
    val meals = arrayOf("Breakfast", "Lunch", "Dinner", "Desert")
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
                            Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }

    return selectedText
}

@Composable
private fun GenerateRecipePopUp(
    recipe: Recipe,
    vm: QueryVM,
    recipeVM: RecipeScreenVM,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = {
            // Dismiss the dialog when the user clicks outside the dialog or on the back
            // button. If you want to disable that functionality, simply use an empty
            // onCloseRequest.
        },
        icon = { Image(painter = /*TODO: make this correct image*/painterResource(id = R.drawable.recipe_test_image), contentDescription = null) },
        title = { Text(text = recipe.title) },
        modifier = modifier,
        dismissButton = {
            Button(
                onClick = {
                    vm.changeAlertValue()
                }
            ) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            Button(onClick = {
                recipeVM.ChangeRecipeTo(recipe)
                navController.navigate(NavScreens.Recipe.route)
                vm.changeAlertValue()
            }) {
                Text(text = "View")
            }
        }
    )
}

