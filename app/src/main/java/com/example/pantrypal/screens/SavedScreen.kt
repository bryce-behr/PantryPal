package com.example.pantrypal.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pantrypal.R
import com.example.pantrypal.viewmodels.DatabaseState
import com.example.pantrypal.viewmodels.DatabaseVM

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedScreen(navController: NavController, modifier: Modifier = Modifier) {

    val vm: DatabaseVM = DatabaseVM.getInstance()
    val vmState: DatabaseState = vm.databaseState
    val recipeList by vm.recipes.collectAsState()
    var xFlag by rememberSaveable { mutableStateOf(false) }

    xFlag = !vmState.searchPhrase.equals("")

    Column (modifier = modifier
        .fillMaxSize()
        //.verticalScroll(ScrollState(0), true)
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


                vm.updateSearchPhrase("")
                vm.updateSearching(false)
            }, enabled = (xFlag)){
                if (xFlag) {
                    Icon(painter = painterResource(id = R.drawable.x), contentDescription = null)
                }
            } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search),
            placeholder = { Text("search") },
            keyboardActions = KeyboardActions(onSearch = {


                if (vmState.searchPhrase.isNotEmpty()) {
                    vm.getSearched()
                    vm.updateSearching(true)
                } else {
                    vm.updateSearching(false)
                }
                this.defaultKeyboardAction(ImeAction.Done)
            } )
        )

        LazyColumn(modifier = modifier.fillMaxWidth(), state = rememberLazyListState()){
            if (vmState.searching) {
                items(vmState.searchedRecipes) {
                    RecipeCard(it, navController = navController)
                }
            } else {
                items(recipeList) {
                    RecipeCard(it, navController = navController)
                }
            }
        }

//        if (vmState.searching){
//            vmState.searchedRecipes.forEach { x ->
//                RecipeCard(x, navController = navController)
//            }
//        } else {
//            recipeList.forEach { x ->
//                RecipeCard(x, navController = navController)
//            }
//        }
    }
}