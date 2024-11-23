package com.mmust.demeter.ui.composables

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mmust.demeter.ViewModels.Auth.UserData
import com.mmust.demeter.ViewModels.ManageGreenHouseViewModel

@Composable
fun AddSensors(user: UserData, context: Context, vm : ManageGreenHouseViewModel) {
    var greenHouseName by remember { mutableStateOf("") }

    var sensors by remember { mutableStateOf(emptyList<String>()) }
    val onNameChange = { text: String -> greenHouseName = text }


    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    var register by remember { mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .imePadding()
            .animateContentSize()
    ){
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ){
            Text(
                "Register Greenhouse",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
            IconButton(
                onClick = {register = !register}
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = null
                )
            }
        }
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(
                    if(register) 400.dp else 0.dp
                )
                .background(Color.Blue)
        ){
            TextField(
                value = greenHouseName,
                onValueChange = onNameChange,
                label = { Text("GreenHouseName") },
                modifier = Modifier
                    .focusRequester(focusRequester),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )

            Row (
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Button(
                    onClick = {
                        vm.updateGreenhouseSensors(
                            context = context,
                            userId = user.userId!!,
                            greenhouseId = greenHouseName,
                            sensors = sensors
                        )
                    }
                ) {
                    Text("Add Green House")
                }
            }


        }
    }
}