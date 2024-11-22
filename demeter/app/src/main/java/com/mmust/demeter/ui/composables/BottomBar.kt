package com.mmust.demeter.ui.composables

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.mmust.demeter.ViewModels.Auth.UserData
import com.mmust.demeter.Views.Routes.MainRoutes

sealed class BottomBarItem(
    val title: String,
    val icon: ImageVector,
    val route: String,
) {
    object Home : BottomBarItem(
        title = "Home",
        icon = Icons.Filled.Home,
        route = MainRoutes.Home.route
    )

    object AddGreenHouse : BottomBarItem(
        title = "Add Green House",
        icon = Icons.Outlined.AddCircle,
        route = MainRoutes.AddGreenhouse.route
    )
}

@Composable
fun BottomBar(navController: NavController,user : UserData? = null) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf(
        BottomBarItem.Home,
        BottomBarItem.AddGreenHouse
    )
    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp,10.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                color = Color(0x344CFF1F)
            )
    ){
        for (item in items) {
            NavBarItem(
                icon = item.icon,
                title = item.title,
                selected = selectedItem == items.indexOf(item),
                onClick = {
                    selectedItem = items.indexOf(item)
                    navController.navigate(item.route)
                }
            )
        }
        Column (
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxHeight(0.08f)
                .padding(10.dp)
                .border(1.dp,Color.Cyan, CircleShape)
                .clip(CircleShape)
                .clickable {
                    navController.navigate(MainRoutes.Profile.route)
                }
        ){
            AsyncImage(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                model = user?.profilePictureUrl,
                contentDescription = "Profile Picture",
                contentScale = ContentScale.FillBounds
            )
        }
    }
}
@Composable
fun NavBarItem(icon: ImageVector, title: String, selected: Boolean, onClick: () -> Unit){
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .animateContentSize()
            .fillMaxHeight(0.08f)
            .fillMaxWidth(
                if (selected) 0.7f else 0.3f
            )
            .padding(5.dp)
            .clip(CircleShape)
            .clickable(true,null,null,onClick)
            .background(
                color = if(selected)
                Color.White
                else
                Color.Transparent
            )
    ){
        Icon(
            modifier = Modifier
                .size(
                    if (selected) 40.dp else 30.dp
                ),
            imageVector = icon,
            contentDescription = title,
            tint = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(1.dp))
        if(selected)
        Text(
            text = title,
            color = Color.Black,
            fontSize = 16.sp
        )
    }
}











//    NavigationBar {
//        items.forEachIndexed { index, item ->
//            NavigationBarItem(
//                icon = { Icon(item.icon, contentDescription = null) },
//                label = { Text(item.title) },
//                selected = selectedItem == index,
//                onClick = {
//                    selectedItem = index
//                    navController.navigate(item.route)
//                }
//            )
//        }
//    }