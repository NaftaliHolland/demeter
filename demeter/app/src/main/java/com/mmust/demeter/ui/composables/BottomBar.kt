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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.type.Fraction
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
        title = "Add",
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
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.09f)
            .padding(15.dp, 10.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                color = Color(0x344CFF1F)
            )
    ){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween ,
            modifier = Modifier
                .weight(5f)
                .fillMaxWidth()
                .fillMaxHeight()
        ){
            for (item in items) {
                NavBarItem(
                    icon = item.icon,
                    title = item.title,
                    selected = selectedItem == items.indexOf(item),
                    onClick = {
                        selectedItem = items.indexOf(item)
                        navController.navigate(item.route)
                    },
                    selectedIndex = selectedItem
                )
            }
        }
        Row (
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ){
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .padding(2.dp)
                    .border(1.dp, Color.Cyan, CircleShape)
                    .clip(CircleShape)
                    .background(Color.LightGray)
                    .clickable {
                        navController.navigate(MainRoutes.Profile.route)
                    }
            ) {
                if (user?.profilePictureUrl != null)
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        model = user.profilePictureUrl,
                        contentDescription = "Profile Picture",
                        contentScale = ContentScale.FillBounds
                    )
                else
                    Text(
                        modifier = Modifier
                            .widthIn(min = 50.dp),
                        text = user?.initial?.uppercase() ?: "Err",
                        textAlign = TextAlign.Center,
                        fontSize = 30.sp,
                    )

            }
        }
    }
}
@Composable
fun NavBarItem(icon: ImageVector, title: String, selected: Boolean, onClick: () -> Unit, selectedIndex: Int){
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(5.dp)
            .animateContentSize()
            .fillMaxHeight(0.95f)
            .fillMaxWidth(
                if(selectedIndex == 0){
                    if (selected) 0.85f else 0.85f
                }else{
                    if (selected) 0.99f else 0.15f
                }
            )
            .clip(CircleShape)
            .clickable(true, null, null, onClick)
            .background(
                color = if (selected)
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