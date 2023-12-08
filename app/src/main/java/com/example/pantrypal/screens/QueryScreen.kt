package com.example.pantrypal.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.PathNode
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pantrypal.R
import com.example.pantrypal.deviceSize
import com.example.pantrypal.models.Ingredient
import com.example.pantrypal.viewmodels.DatabaseVM
import com.example.pantrypal.viewmodels.QueryVM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Query(modifier: Modifier = Modifier, VM: QueryVM) {

    var update by rememberSaveable { mutableStateOf(true) }

    val configuration = LocalConfiguration.current
    deviceSize.screenWidth = configuration.screenWidthDp.dp.value
    deviceSize.screenHeight = configuration.screenHeightDp.dp.value

    if(update) {
        Text("")
    }

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
                for(i in 0 until VM.ingredients.size) {
                    update = !update
                    Ingredient(modifier = modifier
                        .fillMaxWidth()
                        .padding(15.dp), name = VM.ingredients[i], vm = VM)
                }
            }

//            Text(text = VM.getIngredients(),//"Ingredients:\n- 2 Chicken Breasts\n- Ranch\n- Potatoes",
//                modifier = modifier
//                    .fillMaxSize()
//                    .padding(15.dp)
//            )
        }

        Spacer(modifier = modifier.weight(.4f))

        Text(text = "Ingredients:",
            modifier = modifier.padding(15.dp),
            fontSize = 20.sp)

        Row(modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
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
                        VM.ingredients.add(ingText)
                        ingText = ""
                        update = !update
                    }
                })
            )

            Button(onClick = {
                if(ingText != ""){
                    VM.ingredients.add(ingText)
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

//        var expanded by remember { mutableStateOf(false) }
//        var selectedText by remember { mutableStateOf("") }
//
//        ExposedDropdownMenuBox(modifier = modifier
//            .fillMaxWidth()
//            .weight(1f), expanded = true, onExpandedChange = {}) {
//            TextField(
//                value = selectedText,
//                onValueChange = {},
//                readOnly = true,
//                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
//                modifier = Modifier.menuAnchor()
//            )
//            ExposedDropdownMenu(expanded = true, onDismissRequest = { expanded = false }) {
//                DropdownMenuItem(text = { Text("Breakfast") }, onClick = { selectedText = "sdf" })
//                DropdownMenuItem(text = { Text("Lunch") }, onClick = { selectedText = "sdf" })
//                DropdownMenuItem(text = { Text("Dinner") }, onClick = { selectedText = "sdf" })
//
//            }
//        }

        Text(text = "Meal",
            modifier = modifier.padding(15.dp),
            fontSize = 20.sp
        )

        Row (modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
            horizontalArrangement = Arrangement.SpaceEvenly){
            TextField(value = "", onValueChange = {},
                modifier = modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(end = 16.dp))

            Button(onClick = { /*TODO*/ },
                shape = RectangleShape,
                modifier = modifier
                    .weight(.333f)
                    .fillMaxHeight()) {
                Text(text = "Add", fontSize = 20.sp)
            }
        }

        Spacer(modifier = modifier.weight(.4f))

        Text(text = "Category",
            modifier = modifier.padding(15.dp),
            fontSize = 20.sp)

        Row (modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
            horizontalArrangement = Arrangement.SpaceEvenly){
            TextField(value = "", onValueChange = {},
                modifier = modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(end = 16.dp))

            Button(onClick = { /*TODO*/ },
                shape = RectangleShape,
                modifier = modifier
                    .weight(.333f)
                    .fillMaxHeight()) {
                Text(text = "Add", fontSize = 20.sp)
            }
        }

        Spacer(modifier = modifier.weight(.8f))

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
        Text(name)
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

