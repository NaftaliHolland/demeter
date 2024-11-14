package com.mmust.demeter.Views.Profile

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mmust.demeter.ViewModels.Auth.UserData

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
            Column {
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
                        Text(text = "2 Farms | 6 green houses")
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
                        contentDescription = "Profile Picture"
                    )
                }
            }
        }
        Spacer(Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(
                    RoundedCornerShape(
                        topStart = 50.dp,
                        topEnd = 50.dp,

                        )
                )
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0x5BD1DED1),
                            Color(0x5B606260),
                        ),
                        start = Offset.Zero,
                        end = Offset.Infinite
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Button(
                    onClick = logout
                ) {
                    Text(text = "Logout")
                }
            }
        }
    }
}