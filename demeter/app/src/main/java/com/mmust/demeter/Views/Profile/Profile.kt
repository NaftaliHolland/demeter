package com.mmust.demeter.Views.Profile

import com.mmust.demeter.ViewModels.Auth.UserData
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun Profile(
    userData: UserData?,
    logout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 10.dp, 0.dp, 0.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 19.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
            ) {
                userData?.username?.let {
                    Text(
                        text = it,
                        fontSize = 28.sp,
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
                        Text(text = "10 Farms | 6 green houses")
                    }
                }
            }
            userData?.profilePictureUrl?.let {
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color(0xFF46FF37), CircleShape)
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        model = it,
                        contentDescription = "Profile Picture",
                        contentScale = ContentScale.FillBounds
                    )
                }
            }
        }
        Spacer(Modifier.height(20.dp))
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .clip(
                    RoundedCornerShape(
                        topStart = 50.dp,
                        topEnd = 50.dp,

                        )
                )
                .background(Color.LightGray)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 1.dp,
                        top = 30.dp,
                        end = 1.dp,
                        bottom = 20.dp
                    )
                    .clip(RoundedCornerShape(40.dp))
            ) {
                Column(
                    modifier = Modifier
                        .padding(15.dp, 0.dp)
                ) {
                    Text(
                        text = "My farms",
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(10.dp, 0.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                )
                {

                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                    ){
                        Icon(imageVector = Icons.Outlined.Settings, contentDescription = null)
                        Text(text = "Settings ", fontSize = 30.sp)
                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.15f)
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(0.7f),
                        onClick = logout
                    ) {
                        Text(text = "Log Out")
                    }
                }
            }
        }
    }
}