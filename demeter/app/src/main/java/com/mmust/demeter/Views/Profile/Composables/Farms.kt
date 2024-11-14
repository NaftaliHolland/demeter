package com.mmust.demeter.Views.Profile.Composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mmust.demeter.R
import androidx.compose.ui.Modifier

@Composable
fun FarmCard(id: Int) {
    Row(
        modifier = Modifier
            .padding(5.dp, 5.dp)
    ) {
        Box {
            Image(
                modifier = Modifier.size(50.dp),
                painter = painterResource(id = R.drawable.greenhouse),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(text = "farm name $id")
            Text(text = "Location")
        }
    }
}