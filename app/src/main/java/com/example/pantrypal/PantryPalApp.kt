package com.example.pantrypal

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pantrypal.Screens.HomeScreen

sealed class NavScreens(val route: String){
    object Home: NavScreens(route = "Home")
    object Query: NavScreens(route = "Query")
    object Saved: NavScreens(route = "Saved")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantryPalApp() {

    val navController = rememberNavController()
    val currentScreenHandler by navController.currentBackStackEntryAsState()

    Scaffold (
        topBar = {
            PantryPalTopBar()
        }
    ){ internalPadding ->
        NavHost(navController = navController,
            startDestination = NavScreens.Home.route){

            composable(route = NavScreens.Home.route){
                HomeScreen()
            }

            composable(route = NavScreens.Query.route){

            }

            composable(route = NavScreens.Saved.route){

            }
        }
    }
}

@Composable
fun PantryPalTopBar(){

}

