package com.example.pantrypal

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pantrypal.screens.HomeScreen
import com.example.pantrypal.screens.Query
import com.example.pantrypal.screens.RecipeScreen
import com.example.pantrypal.screens.SavedScreen
import com.example.pantrypal.screens.SettingsScreen
import com.example.pantrypal.viewmodels.DatabaseVM
import com.example.pantrypal.viewmodels.HomeScreenState
import com.example.pantrypal.viewmodels.HomeScreenVM
import com.example.pantrypal.viewmodels.QueryVM
import com.example.pantrypal.viewmodels.RecipeState
import com.example.pantrypal.viewmodels.RecipeVM

sealed class NavScreens(val route: String, @StringRes val resourceId: Int){
    object Home: NavScreens(route = "Home", R.string.home)
    object Query: NavScreens(route = "Query", R.string.query)
    object Saved: NavScreens(route = "Saved", R.string.saved)
    object Settings: NavScreens(route = "Settings", R.string.settings)
    object Recipe: NavScreens(route = "Recipe", R.string.recipe)
}

object deviceSize{
    var screenWidth: Float? = null
    var screenHeight: Float? = null
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantryPalApp(){

    val dbVM: DatabaseVM = DatabaseVM.getInstance()
    val queryVM: QueryVM = QueryVM.getInstance()
    val recipeVM: RecipeVM = RecipeVM.getInstance()
    val recipeState: RecipeState = recipeVM.recipeState
    val homeScreenVM: HomeScreenVM = HomeScreenVM.getInstance()
    val homeScreenState: HomeScreenState = homeScreenVM.homeScreenState
    val db: DatabaseVM = DatabaseVM.getInstance()
    val idList by db.recipeAndImageIDS.collectAsState()
    val titles by db.titles.collectAsState()

    val navController = rememberNavController()
    val currentScreenHandler by navController.currentBackStackEntryAsState()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination

    var saved by rememberSaveable { mutableStateOf(false) }



    Scaffold(
        topBar = {
            if(currentRoute?.route != NavScreens.Recipe.route) {
                PantryPalTopBar(goToSaved = {
                    navController.navigate(NavScreens.Saved.route) {
                        if (currentRoute?.route == NavScreens.Query.route) navController.popBackStack()
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }, goToHome = {
                    navController.navigate(NavScreens.Home.route) {
                        if (currentRoute?.route == NavScreens.Query.route) navController.popBackStack()
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }, goToSettings = {
                    navController.navigate(NavScreens.Settings.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                })

                if ((currentRoute?.route == NavScreens.Home.route)&&(homeScreenState.searchFlag)){
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .height(75.dp)
                        .background(Color.hsv(158f, 1f, .2f, 1f)),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically) {

                        IconButton(modifier = Modifier
                            .size(50.dp)
                            .padding(start = 15.dp), onClick = {
                            homeScreenVM.updateSearchFlag(false)
                            homeScreenVM.updateSearchPhrase("")
                        }) {
                            Icon(modifier = Modifier.fillMaxSize(), painter = painterResource(id = R.drawable.back), contentDescription = null, tint = Color.White)
                        }
                    }
                }
            } else {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp)
                    .background(Color.hsv(158f, 1f, .2f, 1f)),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically) {

                    IconButton(modifier = Modifier
                        .size(50.dp)
                        .padding(start = 15.dp), onClick = {
                        navController.popBackStack()
                        recipeVM.clearRecipeQuery()
                    }) {
                        Icon(modifier = Modifier.fillMaxSize(), painter = painterResource(id = R.drawable.back), contentDescription = null, tint = Color.White)
                    }

/*
                    when (recipeState){
                        is RecipeState.Success -> {
                            Text(recipeState.recipe.title, fontSize = 40.sp, fontWeight = FontWeight.ExtraBold, color = Color.White, textAlign = TextAlign.Center, modifier = Modifier.padding(start = 30.dp))
                        }
                        is RecipeState.LoadingSuccess -> {
                            Text(recipeState.recipe.title, fontSize = 40.sp, fontWeight = FontWeight.ExtraBold, color = Color.White, textAlign = TextAlign.Center, modifier = Modifier.padding(start = 30.dp))
                        }
                        is RecipeState.HalfSuccess -> {
                            Text(recipeState.recipe.title, fontSize = 40.sp, fontWeight = FontWeight.ExtraBold, color = Color.White, textAlign = TextAlign.Center, modifier = Modifier.padding(start = 30.dp))
                        }
                        is RecipeState.Loading -> {
                            Text("Recipe Loading...", fontSize = 40.sp, fontWeight = FontWeight.ExtraBold, color = Color.White, textAlign = TextAlign.Center, modifier = Modifier.padding(start = 30.dp))
                        }
                        is RecipeState.Error -> {
                            Text("Error loading recipe", fontSize = 40.sp, fontWeight = FontWeight.ExtraBold, color = Color.White, textAlign = TextAlign.Center, modifier = Modifier.padding(start = 30.dp))
                        }
                    }
*/
                }
            }
        },
        floatingActionButton = {
            if(currentRoute?.route == NavScreens.Home.route || currentRoute?.route == NavScreens.Saved.route) {
                Button(onClick = {
                    navController.navigate(NavScreens.Query.route)
                    queryVM.ingredients.clear()
                },
                    contentPadding = PaddingValues(0.dp),
                shape = RoundedCornerShape(100),
                colors = ButtonDefaults.outlinedButtonColors(MaterialTheme.colorScheme.onPrimary),
                modifier = Modifier.size(100.dp)) {
                    Icon(painter = painterResource(id = R.drawable.add),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            /*.background(Color.White)*/,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            else if(currentRoute?.route == NavScreens.Recipe.route) {
                when (recipeState){
                    is RecipeState.Success -> {
                        IconButton(onClick = {
                            if (recipeState.recipe.recipeAndImageID != 0) {
                                if (idList.contains(recipeState.recipe.recipeAndImageID)) {
                                    db.deleteRecipe(recipeState.recipe.recipeAndImageID)
                                    saved = idList.contains(recipeState.recipe.recipeAndImageID)
                                } else {
                                    db.insertRecipe(recipeState.recipe)
                                    saved = idList.contains(recipeState.recipe.recipeAndImageID)
                                }
                            } else {
                                if (titles.contains(recipeState.recipe.title)) {
                                    db.deleteRecipe(recipeState.recipe.title)
                                    saved = titles.contains(recipeState.recipe.title)
                                } else {
                                    db.insertRecipe(recipeState.recipe)
                                    saved = titles.contains(recipeState.recipe.title)
                                }
                            }
                        },
                            modifier = Modifier.size(100.dp)) {
                            if ((recipeState.recipe.recipeAndImageID != 0)) {
                                saved = idList.contains(recipeState.recipe.recipeAndImageID)
                            } else {
                                saved = titles.contains(recipeState.recipe.title)
                            }

                            Icon(painter = painterResource(id = R.drawable.bookmark),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize(),
                                tint = if(saved) Color.Red else Color.White
                            )
                        }
                    }
                    is RecipeState.LoadingSuccess -> {
//                        println("ID2: " + recipeState.recipe.recipeAndImageID)
//                        IconButton(onClick = {
//                            println("ID2: " + recipeState.recipe.recipeAndImageID)
//                            if (recipeState.recipe.recipeAndImageID != 0) {
//                                if (idList.contains(recipeState.recipe.recipeAndImageID)) {
//                                    db.deleteRecipe(recipeState.recipe.recipeAndImageID)
//                                    saved = idList.contains(recipeState.recipe.recipeAndImageID)
//                                } else {
//                                    db.insertRecipe(recipeState.recipe)
//                                    saved = idList.contains(recipeState.recipe.recipeAndImageID)
//                                }
//                            } else {
//                                if (titles.contains(recipeState.recipe.title)) {
//                                    db.deleteRecipe(recipeState.recipe.title)
//                                    saved = titles.contains(recipeState.recipe.title)
//                                } else {
//                                    db.insertRecipe(recipeState.recipe)
//                                    saved = titles.contains(recipeState.recipe.title)
//                                }
//                            }
//                        },
//                            modifier = Modifier.size(100.dp),
//                            enabled = false) {
//                            if ((recipeState.recipe.recipeAndImageID != 0)) {
//                                saved = idList.contains(recipeState.recipe.recipeAndImageID)
//                            } else {
//                                saved = titles.contains(recipeState.recipe.title)
//                            }
//
//                            Icon(painter = painterResource(id = R.drawable.bookmark),
//                                contentDescription = null,
//                                modifier = Modifier
//                                    .fillMaxSize(),
//                                tint = if(saved) Color.Red else Color.White
//                            )
//                        }
                    }
                    is RecipeState.HalfSuccess -> {
                        IconButton(onClick = {
                            if (recipeState.recipe.recipeAndImageID != 0) {
                                if (idList.contains(recipeState.recipe.recipeAndImageID)) {
                                    db.deleteRecipe(recipeState.recipe.recipeAndImageID)
                                    saved = idList.contains(recipeState.recipe.recipeAndImageID)
                                } else {
                                    db.insertRecipe(recipeState.recipe)
                                    saved = idList.contains(recipeState.recipe.recipeAndImageID)
                                }
                            } else {
                                if (titles.contains(recipeState.recipe.title)) {
                                    db.deleteRecipe(recipeState.recipe.title)
                                    saved = titles.contains(recipeState.recipe.title)
                                } else {
                                    db.insertRecipe(recipeState.recipe)
                                    saved = titles.contains(recipeState.recipe.title)
                                }
                            }
                        },
                            modifier = Modifier.size(100.dp)) {
                            if ((recipeState.recipe.recipeAndImageID != 0)) {
                                saved = idList.contains(recipeState.recipe.recipeAndImageID)
                            } else {
                                saved = titles.contains(recipeState.recipe.title)
                            }

                            Icon(painter = painterResource(id = R.drawable.bookmark),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize(),
                                tint = if(saved) Color.Red else Color.White
                            )
                        }
                    }
                    is RecipeState.Loading -> {}
                    is RecipeState.Error -> {}
                }
            }
        }
    ){ internalPadding ->
        NavHost(navController = navController,
            startDestination = NavScreens.Home.route)
        {

            composable(route = NavScreens.Home.route){
                HomeScreen(navController = navController)
            }

            composable(route = NavScreens.Query.route){
                Query(navController = navController)
            }

            composable(route = NavScreens.Saved.route){
                SavedScreen(navController = navController)
            }

            composable(route = NavScreens.Settings.route) {
                SettingsScreen()
            }
            
            composable(route = NavScreens.Recipe.route) {
                RecipeScreen()
            }
        }
    }
}

@Composable
fun PantryPalTopBar(goToSaved: ()->Unit, goToHome: ()->Unit, goToSettings: ()->Unit){
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(75.dp)
        .background(MaterialTheme.colorScheme.primary),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically) {

        IconButton(modifier = Modifier.size(50.dp), onClick = {goToHome()}) {
            Icon(modifier = Modifier.fillMaxSize(), painter = painterResource(id = R.drawable.home), contentDescription = null, tint = Color.White)
        }
        IconButton(modifier = Modifier.size(50.dp), onClick = {goToSaved()}) {
            Icon(modifier = Modifier.fillMaxSize(), painter = painterResource(id = R.drawable.bookmark), contentDescription = null, tint = Color.White)
        }
        IconButton(modifier = Modifier.size(50.dp), onClick = {goToSettings()}) {
            Icon(modifier = Modifier.fillMaxSize(), painter = painterResource(id = R.drawable.settings), contentDescription = null, tint = Color.White)
        }
    }
}

