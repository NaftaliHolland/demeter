package com.mmust.demeter.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import com.mmust.demeter.ViewModels.Auth.UserData

@Composable
fun ProfilePicture(userData: UserData?) {
    if (userData?.profilePictureUrl != null) {
        Box(
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
                .border(1.dp, Color(0xFF46FF37), CircleShape)
        ) {
            AsyncImage(
                model = userData.profilePictureUrl,
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    } else {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
                .border(1.dp, Color(0xFF46FF37), CircleShape)
        ) {
            Text(
                text = userData?.initial?.uppercase() ?: "?",
                fontSize = 48.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}