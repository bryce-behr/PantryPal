package com.example.pantrypal.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pantrypal.R
import com.example.pantrypal.database.Recipe
import com.example.pantrypal.viewmodels.HomeScreenState
import com.example.pantrypal.viewmodels.HomeScreenVM
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavController) {

    val vm: HomeScreenVM = HomeScreenVM.getInstance()
    val vmState: HomeScreenState = vm.homeScreenState
    var xFlag by rememberSaveable { mutableStateOf(false) }

    xFlag = !vmState.searchPhrase.equals("")

    Column (modifier = modifier
        .fillMaxSize()
        //.verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier
            .height(75.dp)
            .fillMaxWidth()
        )

        TextField(value = vmState.searchPhrase, onValueChange = {
            vm.updateSearchPhrase(it)
        },
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            singleLine = true,
            leadingIcon = { Icon(painter = painterResource(id = R.drawable.search), contentDescription = null) },
            trailingIcon = { IconButton(onClick = {
                println("clicked")
                vm.updateSearchPhrase("")
                vm.updateSearchFlag(false)
            }, enabled = (xFlag)){
                if (xFlag) {
                    Icon(painter = painterResource(id = R.drawable.x), contentDescription = null)
                }
            } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search),
            placeholder = { Text("search") },
            keyboardActions = KeyboardActions(onSearch = {

                vm.updateSearchFlag(true)
                vm.searchForRecipes()

                this.defaultKeyboardAction(ImeAction.Done)
            } )
        )

        if (vmState.searchPhrase.equals("")){
            Row (modifier = modifier.fillMaxWidth().padding(bottom = 15.dp), horizontalArrangement = Arrangement.SpaceEvenly){
                Button(onClick = {
                    vm.updateBreakfastFlag(!vmState.breakfastFlag)
                    println(vmState.breakfastRecipes.size)
                }, colors = ButtonDefaults.buttonColors(
                        containerColor = if(vmState.breakfastFlag){MaterialTheme.colorScheme.primary}else{MaterialTheme.colorScheme.tertiary},
                        contentColor = Color.Black,
                        disabledContainerColor = Color.LightGray,
                        disabledContentColor = Color.Black
                    )
                ) {
                    Text(text = "Breakfast")
                }
                Button(onClick = {
                    vm.updateLunchFlag(!vmState.lunchFlag)
                    println(vmState.largeList.size)
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = if(vmState.lunchFlag){MaterialTheme.colorScheme.primary}else{MaterialTheme.colorScheme.tertiary},
                    contentColor = Color.Black,
                    disabledContainerColor = Color.LightGray,
                    disabledContentColor = Color.Black
                )) {
                    Text(text = "Lunch")
                }
                Button(onClick = {
                    vm.updateDinnerFlag(!vmState.dinnerFlag)
                    println(vmState.largeList.size)
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = if(vmState.dinnerFlag){MaterialTheme.colorScheme.primary}else{MaterialTheme.colorScheme.tertiary},
                    contentColor = Color.Black,
                    disabledContainerColor = Color.LightGray,
                    disabledContentColor = Color.Black
                )) {
                    Text(text = "Dinner")
                }
                Button(onClick = {
                    vm.updateDessertFlag(!vmState.dessertFlag)
                    println(vmState.largeList.size)
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = if(vmState.dessertFlag){MaterialTheme.colorScheme.primary}else{MaterialTheme.colorScheme.tertiary},
                    contentColor = Color.Black,
                    disabledContainerColor = Color.LightGray,
                    disabledContentColor = Color.Black
                )) {
                    Text(text = "Dessert")
                }
            }
        }

        LazyColumn(modifier = modifier.fillMaxWidth(), state = rememberLazyListState()){
            if (!vmState.searchFlag) {
                items(vmState.largeList) {
                    RecipeCard(it, navController = navController)
                }
            } else {
                items(vmState.searchRecipes) {
                    RecipeCard(it, navController = navController)
                }
            }
        }

//        if (!vmState.searchFlag) {
//            vmState.largeList.forEach { x ->
//                RecipeCard(x, navController = navController)
//            }
//        } else {
//            vmState.searchRecipes.forEach { x ->
//                RecipeCard(x, navController = navController)
//            }
//        }
    }
}

