package com.mmust.demeter.Views.NewGreenHouse

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.mmust.demeter.ViewModels.ManageGreenHouseViewModel
import com.mmust.demeter.ViewModels.Auth.UserData
import com.mmust.demeter.ui.composables.CreateGreenhouse


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Add(user: UserData) {
    val context = LocalContext.current
    val viewModel = ManageGreenHouseViewModel(user)
    var greenHouseIds = viewModel.greenhouseIds.observeAsState().value
    lateinit var greenhouseId : String

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 36.dp, 0.dp, 85.dp)
    ) {
//        LazyColumn {
//            items(greenHouseIds!!.size) {
//                Text(text = greenHouseIds[it])
//            }
//        }

        CreateGreenhouse(user,context,viewModel)



    }
}
