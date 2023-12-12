package com.example.pantrypal.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pantrypal.NavScreens
import com.example.pantrypal.R
import com.example.pantrypal.database.Recipe
import com.example.pantrypal.deviceSize
import com.example.pantrypal.viewmodels.RecipeVM

@Composable
fun RecipeCard(recipe: Recipe, modifier: Modifier = Modifier, navController: NavController) {

    val recipeVM: RecipeVM = RecipeVM.getInstance()

    val configuration = LocalConfiguration.current
    deviceSize.screenWidth = configuration.screenWidthDp.dp.value
    deviceSize.screenHeight = configuration.screenHeightDp.dp.value

    var saved by rememberSaveable { mutableStateOf(false) }

    Box(contentAlignment = Alignment.BottomEnd,
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
            .requiredWidth(((deviceSize.screenWidth ?: 100f) - 16f).dp)
    )
    {
        Card(
            modifier = modifier
                .clickable(onClick = {
                    recipeVM.loadRecipe(recipe)
                    navController.navigate(NavScreens.Recipe.route)
                })
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(recipe.image)
                    .crossfade(true)
                    .build(),
                contentDescription = recipe.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(180.dp)
            )

            Row(modifier = modifier
                .height(50.dp)){
                Text(
                    text = "this is the really long title for testing purposes. It should overflow onto the next line",//recipe.title,
                    style = LocalTextStyle.current.merge(
                        TextStyle(
                            lineHeight = 3.em,
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            ),
                            lineHeightStyle = LineHeightStyle(
                                alignment = LineHeightStyle.Alignment.Center,
                                trim = LineHeightStyle.Trim.None
                            )
                        )
                    ),
                    overflow = TextOverflow.Ellipsis,
                    softWrap = true,
                    modifier = modifier
                        .fillMaxHeight()
                        .padding(start = 16.dp)
                        .weight(1f)
                )

                Icon(
                    painter = painterResource(id = R.drawable.bookmark),
                    contentDescription = null,
                    tint = if(saved) Color.Red else Color.White,
                    modifier = modifier
                        .weight(.2f)
                        .fillMaxHeight()
                        .clickable {
                            saved = !saved
                        }
                        .padding(5.dp)
                )
            }
        }

    }
}

