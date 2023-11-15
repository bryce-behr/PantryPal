package com.example.pantrypal.Screens

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Query(modifier: Modifier = Modifier) {
    Column{
        Box(modifier = modifier
            .height(100.dp)
            .width(100.dp)
            .verticalScroll(ScrollState(0), true)
        ){
            Text(text = "Test",
                modifier = modifier.fillMaxSize())
        }
    }

}

