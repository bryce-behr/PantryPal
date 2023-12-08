package com.example.pantrypal.screens

import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.RowScopeInstance.align
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pantrypal.R
import com.example.pantrypal.deviceSize
import com.example.pantrypal.viewmodels.QueryVM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Query(modifier: Modifier = Modifier, vm: QueryVM) {

    var update by rememberSaveable { mutableStateOf(true) }

    val configuration = LocalConfiguration.current
    deviceSize.screenWidth = configuration.screenWidthDp.dp.value
    deviceSize.screenHeight = configuration.screenHeightDp.dp.value

//    if(update) {
//        Text("")
//    }

    Column(modifier = modifier.padding(15.dp)){

        Spacer(modifier = modifier.height(75.dp))

        Box(modifier = modifier
            .fillMaxWidth()
            .height((deviceSize.screenHeight!! / 4).dp)
            .border(10.dp, Color.Black)
        ){
            Column(modifier = modifier
                .fillMaxSize()
                .verticalScroll(ScrollState(0), true)
            ){
                Spacer(modifier = modifier.height(10.dp))
                for(i in 0 until vm.ingredients.size) {
                    update = !update
                    Ingredient(modifier = modifier
                        .fillMaxWidth()
                        .padding(5.dp), name = vm.ingredients[i], vm = vm)
                }
            }
        }

        Spacer(modifier = modifier.weight(.4f))

        Text(text = "Ingredients:",
            modifier = modifier.padding(15.dp),
            fontSize = 20.sp)

        Row(modifier = Modifier
            .fillMaxWidth()
            .weight(.25f),
            horizontalArrangement = Arrangement.SpaceEvenly){
            var ingText by rememberSaveable { mutableStateOf("") }
            TextField(value = ingText, onValueChange = { ingText = it },
                modifier = modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(end = 16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onAny = {
                    this.defaultKeyboardAction(ImeAction.Done)
                    if(ingText != "") {
                        vm.ingredients.add(ingText)
                        ingText = ""
                        update = !update
                    }
                })
            )

            Button(onClick = {
                if(ingText != ""){
                    vm.ingredients.add(ingText)
                    ingText = ""
                    update = !update
                }
            },
                shape = RectangleShape,
                modifier = modifier
                    .weight(.333f)
                    .fillMaxHeight()/*.padding(start = 16.dp, end = 16.dp)*/) {
                Text(text = "Add", fontSize = 20.sp, textAlign = TextAlign.Center)
            }
        }

        Spacer(modifier = modifier.weight(.4f))

        ExposedDropdownMenuBox(modifier = modifier.fillMaxWidth().height(50.dp), vm = vm)

        Spacer(modifier = modifier.weight(2f))

        Row(horizontalArrangement = Arrangement.End, modifier = Modifier
            .fillMaxWidth()
            .weight(1f)){
            Button(onClick = { /*TODO*/ },
                shape = RectangleShape,
                modifier = modifier.fillMaxHeight()) {
                Text(text = "Generate")
            }
        }
    }
}

@Composable
fun Ingredient(modifier: Modifier = Modifier, name: String, vm: QueryVM) {
    Row(modifier = modifier
        .fillMaxWidth()
        .height(25.dp)) {
        Text("- $name", fontSize = 20.sp)
        IconButton(onClick = {
            vm.ingredients.remove(name)
            println(vm.ingredients.toString())
        }) {
            Icon(
                modifier = Modifier.fillMaxHeight(),
                painter = painterResource(id = R.drawable.remove),
                contentDescription = null,
                tint = Color.Red
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenuBox(modifier: Modifier = Modifier, vm: QueryVM) {
    val context = LocalContext.current
    val meals = arrayOf("Breakfast", "Lunch", "Dinner", "Desert")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(meals[2]) }

    Box (modifier = Modifier
        .fillMaxWidth()
    ){
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                meals.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedText = item
                            vm.meal = selectedText
                            expanded = false
                            Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}

