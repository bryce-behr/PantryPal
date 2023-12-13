package com.example.pantrypal.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pantrypal.R
import com.example.pantrypal.database.Recipe
import com.example.pantrypal.viewmodels.HomeScreenState
import com.example.pantrypal.viewmodels.HomeScreenVM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavController) {

    val vm: HomeScreenVM = HomeScreenVM.getInstance()
    val vmState: HomeScreenState = vm.homeScreenState
    var xFlag by rememberSaveable { mutableStateOf(false) }

    xFlag = !vmState.searchPhrase.equals("")

    Column (modifier = modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
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

        if (!vmState.searchFlag) {
            vmState.largeList.forEach { x ->
                RecipeCard(x, navController = navController)
            }
        } else {
            vmState.searchRecipes.forEach { x ->
                RecipeCard(x, navController = navController)
            }
        }
    }
}

