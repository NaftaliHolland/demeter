package com.mmust.demeter.Views.Profile

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.test.services.storage.file.PropertyFile.Column
import com.mmust.demeter.ViewModels.Auth.UserData
import com.mmust.demeter.ViewModels.ProfileViewModel
import com.mmust.demeter.ui.composables.ProfilePicture

@Composable
fun Profile(
    userData: UserData? = null,
    logout: () -> Unit = {}
) {
    val vm = ProfileViewModel(userData)
    val greenHouses = vm.greenhouseIds.collectAsState()


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 20.dp)
        ) {
            Column(modifier = Modifier.weight(2f)) {
                userData?.username?.let {
                    Text(
                        text = it,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                userData?.mail?.let {
                    Text(
                        text = it,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(Modifier.height(5.dp))
                userData?.let {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0x3A6EF161))
                            .padding(10.dp, 5.dp)
                    ) {
                        Text(text = "10 Farms | 6 greenhouses")
                    }
                }
            }
            ProfilePicture(userData)
        }

        Spacer(Modifier.height(20.dp))

        Text(
            text = "My Greenhouses",
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(10.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0x9ED2E7D9))
        ) {
            val greenhousesList = greenHouses.value ?: emptyList()
            if (greenhousesList.isEmpty()) {
                item {
                    Text(
                        text = "You have no greenhouses",
                        modifier = Modifier.padding(16.dp),
                        fontSize = 16.sp
                    )
                }
            } else {
                items(greenhousesList) { greenhouse ->
                    ProfileGreenHouseCard(uid = userData?.userId,greenhouseName = greenhouse, vm = vm)
                }
            }
        }
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {

        }

        Button(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .padding(20.dp, 0.dp, 20.dp, 70.dp),
            onClick = logout
        ) {
            Text(text = "Log Out")
            Spacer(Modifier.width(8.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ExitToApp,
                contentDescription = "Log Out"
            )
        }
    }
}



@Composable
fun ProfileGreenHouseCard(uid : String?,greenhouseName: String ,vm : ProfileViewModel) {
    var more by remember {mutableStateOf(false)}
    var sensors = remember { mutableStateListOf<String>() }
    if (uid != null) {
        vm.getGreenhouseSensors(
            userId = uid,
            greenhouseId = greenhouseName,
            onSuccess = { sensors.clear(); if (it != null) {
                sensors.addAll(it)
            }
            },
            onFailure = {}
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(horizontal = 16.dp)
            .animateContentSize()
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = greenhouseName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Light
            )
            IconButton(
                onClick = { more = !more }
            ) {
                Icon(
                    imageVector = Icons.Outlined.MoreVert,
                    contentDescription = "More Options"
                )
            }
        }
        if (more) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp,5.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color(0x4AC1C2C1))
            ) {
                if (sensors.isEmpty()) {
                    Text("No sensors available", fontSize = 14.sp)
                } else {
                    sensors.forEach { sensor ->
                        Row(

                        ){
                            Text(sensor, fontSize = 14.sp)

                        }
                    }
                }
            }
        }
    }


}
