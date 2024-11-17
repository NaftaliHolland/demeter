package com.mmust.demeter.Views.Auth

import SignInViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mmust.demeter.R


@Composable
fun AuthPage(){
    val context = LocalContext.current
    val signInViewModel = SignInViewModel(context)
    val coroutineScope = rememberCoroutineScope()
    var email by remember { mutableStateOf("") }
    var pwd by remember { mutableStateOf("") }
    val handleEmail = {it:String ->
        email = it
    }
    val handlePwd = {it : String ->
        pwd = it
    }
    val focus = remember {
        FocusRequester()
    }
    val localFocus = LocalFocusManager.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(22.dp))
            Image(
                painter = painterResource(R.drawable.greenhouse),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(10.dp))
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Demeter",
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Cursive,
            )
        }
        Column {
            Text(
                text = "Welcome back",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
            )
            Text(
                text = "Login to continue where you left off",
                fontWeight = FontWeight.Light,
                fontSize = 18.sp,
            )
            OutlinedTextField(
                value = email,
                onValueChange = handleEmail,
                label = { Text(text = "E-mail") },
                placeholder = { Text(text = "example@mail.com") },
                leadingIcon = { Icon(imageVector = Icons.Outlined.Person, contentDescription = "") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        localFocus.moveFocus(FocusDirection.Down)
                    }
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = pwd,
                onValueChange = handlePwd,
                label = { Text(text = "Password") },
                placeholder = { Text(text = "850_.@23/") },
                maxLines = 1,
                leadingIcon = { Icon(imageVector = Icons.Outlined.Lock, contentDescription = "") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (email.isNotEmpty() && pwd.isNotEmpty())
//                        n.navigate(MainRoutes.Home.route)
                        else
                            focus.requestFocus()
                    }
                )
            )
            Row {
                Text(text = "Forgot Password?")
            }
        }
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row {
                Text(text = "Don't have an account ?  ")
                Text(
                    text = "Sign up",
                    color = Color(0xFF4885B4),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clickable(enabled = true, onClickLabel = null, role = null, {})
                )
            }
            Button(
                contentPadding = PaddingValues(85.dp, 17.dp),
                onClick = {

                },
                colors = ButtonColors(
                    containerColor = Color(0xFF4885B4),
                    contentColor = Color.Black,
                    disabledContentColor = Color.Black,
                    disabledContainerColor = Color.LightGray
                )
            ) {
                Text(text = "Login", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color(0xC9D9E5E5))
                    .border(1.dp, Color.LightGray, CircleShape)
                    .clickable { signInViewModel.signin(context = context) }
                    .padding(11.dp, 9.dp)

            ) {
                Image(modifier = Modifier.size(50.dp),painter = painterResource(R.drawable.google), contentDescription = null)
                Text(text = "Sign in with Google")
            }
        }

    }
}
