package com.mmust.demeter.presentation.common

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mmust.demeter.presentation.navigation.Route.Companion.bottomNavItems

@Composable
fun BottomNavBar(navController: NavController, currentDestination: NavDestination?) {
    NavigationBar {
       bottomNavItems.forEach { item ->
           NavigationBarItem(
               icon = { item.icon?.let {Icon(it, contentDescription=item.label) } },
               label = { Text(item.label) },
               selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
               onClick = {
                   navController.navigate(item.route) {
                       popUpTo(navController.graph.findStartDestination().id) {
                           saveState = true
                       }
                       launchSingleTop = true
                       restoreState = true
                   }
               }
           )
       }
    }
}