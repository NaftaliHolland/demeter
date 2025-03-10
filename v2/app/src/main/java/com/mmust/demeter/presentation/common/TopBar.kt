package com.mmust.demeter.presentation.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopBar() {
   TopAppBar(
       title = { Text("Demeter") },
       actions = {
           IconButton(onClick = { /* TODO: Add menu */ }) {
               Icon(Icons.Default.MoreHoriz, contentDescription = "More")
           }
       }
   )
}