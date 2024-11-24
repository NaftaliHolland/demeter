package com.mmust.demeter.Views.ManageGreenHouse

import android.annotation.SuppressLint
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.mmust.demeter.ViewModels.ManageGreenHouseViewModel
import com.mmust.demeter.ViewModels.Auth.UserData
import com.mmust.demeter.ui.composables.AddDevice
import com.mmust.demeter.ui.composables.CreateGreenhouse


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Add(user: UserData) {
    val context = LocalContext.current
    val viewModel = ManageGreenHouseViewModel(user)

    val focusManager = LocalFocusManager.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 36.dp, 0.dp, 85.dp)
            .pointerInput(Unit){
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {
        CreateGreenhouse(user,context,viewModel)
        AddDevice(user,context,viewModel)


    }
}
