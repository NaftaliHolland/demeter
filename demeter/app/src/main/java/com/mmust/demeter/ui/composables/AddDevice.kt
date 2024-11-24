package com.mmust.demeter.ui.composables

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mmust.demeter.R
import com.mmust.demeter.ViewModels.Auth.UserData
import com.mmust.demeter.ViewModels.ManageGreenHouseViewModel



@Composable
fun AddDevice(user: UserData, context: Context, vm : ManageGreenHouseViewModel) {
    var greenHouseName by remember { mutableStateOf("") }
    var _greenhouses = remember { mutableStateListOf<String>() }
    var greenHouses = vm.greenhouseIds.collectAsState()
    greenHouses.value?.let {
        _greenhouses.addAll(it)
    }
    val inputs = remember {
        mutableStateListOf<String>("")
    }
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    var register by remember { mutableStateOf(false) }

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .animateContentSize()
    ) {
        item{ Divider(modifier = Modifier.padding(25.dp), Color.Black, 1.dp) }


        item{
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ){
                    Image(
                        modifier = Modifier
                            .width(70.dp)
                            .height(50.dp),
                        painter = painterResource(R.drawable.gadget),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds
                    )
                    Text(
                        "Add Device",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                IconButton(
                    onClick = { register = !register }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = null
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(
                        if (register) Dp.Infinity else 0.dp
                    )
            ) {
                Row (
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(70.dp, 10.dp)
                ){
                    Text(
                        text = "Select GreenHouse",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                if (_greenhouses.isEmpty()) {
                    Text(
                        text = "You have ${_greenhouses.size} greenHouses"
                    )
                }
                else{
                    for (gn in _greenhouses) {
                        Text(gn, modifier = Modifier.padding(10.dp))
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(70.dp, 2.dp)
                ) {
                    Text(
                        "Add device ",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(
                        onClick = { inputs.add("") }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Add,
                            contentDescription = null
                        )
                    }
                }
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                ) {

                    inputs.forEachIndexed { index, s ->
                        TextField(
                            value = s,
                            onValueChange = { inputs[index] = it },
                            label = { Text("Sensor $index") },

                            )
                    }
                }

                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Button(
                        enabled = if (_greenhouses.isEmpty() || inputs.get(0).isEmpty()) false else true,
                        onClick = {
                            //update logic
                        }
                    ) {
                        Text("Add Device")
                    }
                }


            }

        }
    }
}

