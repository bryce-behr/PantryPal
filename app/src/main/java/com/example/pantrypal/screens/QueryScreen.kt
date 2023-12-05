package com.example.pantrypal.screens

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pantrypal.deviceSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Query(modifier: Modifier = Modifier) {

    val configuration = LocalConfiguration.current
    deviceSize.screenWidth = configuration.screenWidthDp.dp.value
    deviceSize.screenHeight = configuration.screenHeightDp.dp.value



    Column(modifier = modifier.padding(15.dp)){

        Spacer(modifier = modifier.height(75.dp))

        Box(modifier = modifier
            .fillMaxWidth()
            .height((deviceSize.screenHeight!! / 4).dp)
            .verticalScroll(ScrollState(0), true)
            .border(10.dp, Color.Black)
        ){
            Text(text = "Ingredients:\n- 2 Chicken Breasts\n- Ranch\n- Potatoes",
                modifier = modifier
                    .fillMaxSize()
                    .padding(15.dp)
            )
        }

        Spacer(modifier = modifier.weight(.4f))
        //Spacer(modifier = modifier.height((deviceSize.screenHeight!! /15).dp))

        Text(text = "Ingredient",
            modifier = modifier,//.padding(15.dp),
            fontSize = 20.sp)

        Row (modifier = Modifier.fillMaxWidth().weight(1f),
            horizontalArrangement = Arrangement.SpaceEvenly){
            TextField(value = "", onValueChange = {},
                modifier = modifier.weight(1f).fillMaxHeight().padding(end = 16.dp))

            Button(onClick = { /*TODO*/ },
                shape = RectangleShape,
                modifier = modifier.weight(.333f).fillMaxHeight()/*.padding(start = 16.dp, end = 16.dp)*/) {
                Text(text = "Add", fontSize = 20.sp, textAlign = TextAlign.Center)
            }
        }

        Spacer(modifier = modifier.weight(.4f))
//        Spacer(modifier = modifier.height((deviceSize.screenHeight!! /15).dp))

        Text(text = "Meal",
            modifier = modifier.padding(15.dp),
            fontSize = 20.sp
        )

        Row (modifier = Modifier.fillMaxWidth().weight(1f),
            horizontalArrangement = Arrangement.SpaceEvenly){
            TextField(value = "", onValueChange = {},
                modifier = modifier.weight(1f).fillMaxHeight().padding(end = 16.dp))

            Button(onClick = { /*TODO*/ },
                shape = RectangleShape,
                modifier = modifier.weight(.333f).fillMaxHeight()) {
                Text(text = "Add", fontSize = 20.sp)
            }
        }

        Spacer(modifier = modifier.weight(.4f))

        Text(text = "Category",
            modifier = modifier.padding(15.dp),
            fontSize = 20.sp)

        Row (modifier = Modifier.fillMaxWidth().weight(1f),
            horizontalArrangement = Arrangement.SpaceEvenly){
            TextField(value = "", onValueChange = {},
                modifier = modifier.weight(1f).fillMaxHeight().padding(end = 16.dp))

            Button(onClick = { /*TODO*/ },
                shape = RectangleShape,
                modifier = modifier.weight(.333f).fillMaxHeight()) {
                Text(text = "Add", fontSize = 20.sp)
            }
        }

        Spacer(modifier = modifier.weight(.8f))

        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth().weight(1f)){
            Button(onClick = { /*TODO*/ },
                shape = RectangleShape,
                modifier = modifier.fillMaxHeight()) {
                Text(text = "Generate")
            }
        }
    }

}

