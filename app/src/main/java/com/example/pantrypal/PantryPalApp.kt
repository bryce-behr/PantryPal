package com.example.pantrypal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pantrypal.Screens.HomeScreen
import com.example.pantrypal.Screens.Query

sealed class NavScreens(val route: String){
    object Home: NavScreens(route = "Home")
    object Query: NavScreens(route = "Query")
    object Saved: NavScreens(route = "Saved")
}

object deviceSize{
    var screenWidth: Float? = null
    var screenHeight: Float? = null
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantryPalApp() {

    val navController = rememberNavController()
    val currentScreenHandler by navController.currentBackStackEntryAsState()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        topBar = {
            PantryPalTopBar()
        },
        floatingActionButton = {
            if(currentRoute == NavScreens.Home.route) {
                IconButton(onClick = { navController.navigate(NavScreens.Query.route) }, modifier = Modifier.size(100.dp)) {
                    Icon(painter = painterResource(id = R.drawable.add),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize().background(Color.White),
                        tint = Color.hsv(158f, 1f, .2f, 1f)
                    )
                }
            }
        }
    ){ internalPadding ->
        NavHost(navController = navController,
            startDestination = NavScreens.Query.route)
        {

            composable(route = NavScreens.Home.route){
                HomeScreen()
            }

            composable(route = NavScreens.Query.route){
                Query()
            }

            composable(route = NavScreens.Saved.route){

            }
        }
    }
}

@Composable
fun PantryPalTopBar(){
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(75.dp)
        //.padding(16.dp)
        .background(Color.hsv(158f, 1f, .2f, 1f)),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically) {
//        if(canGoBack) {
//            IconButton(onClick = {goBack()}) {
//                Icon(painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
//                    contentDescription = null)
//            }
//        }
        
        IconButton(modifier = Modifier.size(50.dp), onClick = {}) {
            Icon(modifier = Modifier.fillMaxSize(), painter = painterResource(id = R.drawable.search), contentDescription = null, tint = Color.White)
        }
        IconButton(modifier = Modifier.size(50.dp), onClick = {}) {
            Icon(modifier = Modifier.fillMaxSize(), painter = painterResource(id = R.drawable.bookmark), contentDescription = null, tint = Color.White)
        }
        IconButton(modifier = Modifier.size(50.dp), onClick = {}) {
            Icon(modifier = Modifier.fillMaxSize(), painter = painterResource(id = R.drawable.settings), contentDescription = null, tint = Color.White)
        }
//        if(canGoNext) {
//            IconButton(onClick = {goNext() }) {
//                Icon(painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
//                    contentDescription = null)
//            }
//        }
    }
}

