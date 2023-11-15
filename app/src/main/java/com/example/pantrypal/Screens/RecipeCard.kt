package com.example.pantrypal.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.pantrypal.R

object deviceSize{
    var screenWidth: Float? = null
    var screenHeight: Float? = null
}

@Composable
fun RecipeCard(image: Int, description: String, modifier: Modifier = Modifier) {

    val configuration = LocalConfiguration.current
    deviceSize.screenWidth = configuration.screenWidthDp.dp.value
    deviceSize.screenHeight = configuration.screenHeightDp.dp.value

    Card(modifier = modifier
        .fillMaxSize()
        .padding(8.dp)
        .requiredWidth(((deviceSize.screenWidth ?: 100f) - 16f).dp)
    ){

        Image(painter = painterResource(id = R.drawable.Recipe_Test_Image),
            contentDescription = description,
            modifier = modifier.height(180.dp),
            contentScale = ContentScale.Crop
        )

        Text(text = description,
            maxLines = 1, modifier = modifier.padding(16.dp))
    }
}

