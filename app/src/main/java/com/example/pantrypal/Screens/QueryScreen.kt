package com.example.pantrypal.Screens

import android.sax.RootElement
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.pantrypal.deviceSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Query(modifier: Modifier = Modifier) {

    val configuration = LocalConfiguration.current
    deviceSize.screenWidth = configuration.screenWidthDp.dp.value
    deviceSize.screenHeight = configuration.screenHeightDp.dp.value

    Column{
        Box(modifier = modifier
            .fillMaxWidth()
            .height((deviceSize.screenHeight!! / 4).dp)
            .verticalScroll(ScrollState(0), true)
            .padding(15.dp)
            .border(10.dp, Color.Black)
        ){
            Text(text = "fjkabfuhsdfjdnbfiudffasdFISBFIUFDSBFGU" +
                    "IIUGUNSJKDGFBOUSD",
                modifier = modifier
                    .fillMaxSize()
                    .padding(15.dp)
            )
        }

        Spacer(modifier = modifier.height((deviceSize.screenHeight!! /15).dp))

        Text(text = "Ingredient")

        Row (){
            TextField(value = "", onValueChange = {})

            Button(onClick = { /*TODO*/ }) {
                Text(text = "Add Ingredient")
            }
        }

        Spacer(modifier = modifier.height((deviceSize.screenHeight!! /15).dp))

        Text(text = "Meal")

        Row {
            TextField(value = "", onValueChange = {})

            Button(onClick = { /*TODO*/ }) {
                Text(text = "Add meal type")
            }
        }

        Spacer(modifier = modifier.height((deviceSize.screenHeight!! /15).dp))

        Text(text = "Category")

        Row {
            TextField(value = "", onValueChange = {})

            Button(onClick = { /*TODO*/ }) {
                Text(text = "Add Category")
            }
        }
    }

}

