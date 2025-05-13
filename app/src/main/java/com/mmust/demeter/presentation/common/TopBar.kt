package com.mmust.demeter.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mmust.demeter.presentation.navigation.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val label = Route.bottomNavItems.find { it.route == currentRoute }?.label ?: ""

   TopAppBar(
       modifier = Modifier
           .background(Color.Transparent),
       title = { Text(label) },
       navigationIcon = {
           IconButton(onClick = { navController.popBackStack() }) {
               Icon(
                   Icons.AutoMirrored.Filled.ArrowBack,
                   contentDescription = "Back"
               )
           }
       },
       actions = {
           IconButton(onClick = { /* TODO: Add menu */ }) {
               Icon(Icons.Default.MoreHoriz, contentDescription = "More")
           }
       }
   )
}