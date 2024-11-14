package com.mmust.demeter.Views.Profile.Composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mmust.demeter.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun FarmCard(id: Int) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp, 5.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xAB071009))

    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
        ) {
            Box {
                Image(
                    modifier = Modifier.size(150.dp),
                    painter = painterResource(id = R.drawable.greenhouse),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier

            ) {
                Text(
                    text = "Farm name $id",
                    fontWeight = FontWeight.Medium,
                    lineHeight = 16.sp
                )
                Text(
                    text = "Kakamega",
                    fontSize = 14.sp,
                    lineHeight = 16.sp
                )
            }
        }
//        Icon(
//            imageVector = Icons.Outlined.MoreVert,
//            contentDescription = null
//        )
    }
}