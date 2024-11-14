package com.mmust.demeter.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarItem(
    val title: String,
    val icon: ImageVector
    ) {
    object Home: BottomBarItem(
        title = "Home",
        icon = Icons.Outlined.Home,
    )
    object GreenHouses: BottomBarItem(
        title = "Green Houses",
        icon = Icons.Outlined.Home,
    )
    object Profile: BottomBarItem(
        title = "Profile",
        icon = Icons.Outlined.Person,
    )
}

@Composable
fun BottomBar () {
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf(
        BottomBarItem.Home,
        BottomBarItem.GreenHouses,
        BottomBarItem.Profile
    )

    NavigationBar {
        items.forEachIndexed { index, item ->
        NavigationBarItem(
            icon = { Icon(item.icon, contentDescription = null) },
            label = { Text(item.title) },
            selected = selectedItem == index,
            onClick = { selectedItem = index }
        )
        }
    }
}