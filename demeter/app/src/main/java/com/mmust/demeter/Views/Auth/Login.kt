package com.mmust.demeter.Views.Auth

import com.mmust.demeter.ViewModels.Auth.AuthViewModel
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mmust.demeter.R
import com.mmust.demeter.ViewModels.Auth.AuthState
import com.mmust.demeter.Views.Routes.MainRoutes


@Composable
fun Login(navigate:NavController, signInViewModel: AuthViewModel, authNavController: NavController){
    val context = LocalContext.current
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
    val authState = signInViewModel.authState.observeAsState()
    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Authorised -> navigate.navigate(MainRoutes.Home.route)
            is AuthState.Error -> Toast.makeText(context,
                (authState.value as AuthState.Error).msg, Toast.LENGTH_LONG).show()
            else -> Unit
        }

    }
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    localFocus.clearFocus()
                })
            }
            .imePadding()
            

    ) {
        item{

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
            Spacer(Modifier.height(60.dp))
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
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = ""
                    )
                },
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
                            signInViewModel.logInWithEmail(email, pwd, context, navigate)
                        else
                            focus.requestFocus()
                    }
                )
            )
            Row {
                Text(text = "Forgot Password?")
            }
        }
            Spacer(Modifier.height(30.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Text(text = "Don't have an account ?  ")
                Text(
                    text = "Sign up",
                    color = Color(0xFF4885B4),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clickable(enabled = true, onClickLabel = null, role = null, {
                            authNavController.navigate("signup")
                        })
                )
            }
            Button(
                contentPadding = PaddingValues(105.dp, 17.dp),
                onClick = {
                    if (email.isNotEmpty() && pwd.isNotEmpty())
                        signInViewModel.logInWithEmail(email, pwd, context, navigate)
                    else
                        focus.requestFocus()
                },
                colors = ButtonColors(
                    containerColor = Color(0xFF4885B4),
                    contentColor = Color.Black,
                    disabledContentColor = Color.Black,
                    disabledContainerColor = Color.LightGray
                ),
                enabled = if (email.isNotEmpty() && pwd.isNotEmpty()) {
                    true
                } else {
                    false
                }
            ) {
                Text(text = "Login", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color(0xC9D9E5E5))
                    .border(1.dp, Color.LightGray, CircleShape)
                    .clickable { signInViewModel.signInWithGoogle(context = context, navigate) }
                    .padding(8.dp, 5.dp)

            ) {
                Image(
                    modifier = Modifier.size(50.dp),
                    painter = painterResource(R.drawable.google),
                    contentDescription = null
                )
                Text(text = "Sign in with Google")
            }
        }
    }
    }
}
