package com.mmust.demeter.Views.Auth

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mmust.demeter.Views.Routes.AuthRoutes
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
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
import com.mmust.demeter.Models.Auth.SignInState


@Composable
fun AuthPage(
    state: SignInState,
    onSignInClick: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = AuthRoutes.Login.route) {
        composable(AuthRoutes.Login.route) {
            Login(
                onSignInClick = onSignInClick
            )
        }
        composable(AuthRoutes.SignUp.route) {

        }
    }
}

@Composable
fun Login(onSignInClick: () -> Unit) {
    var email by remember { mutableStateOf("") }

    var pwd by remember { mutableStateOf("") }

    val handdleEmail = { it: String ->
        email = it
    }
    val handlePwd = { it: String ->
        pwd = it
    }
    val logScroll = rememberScrollState()
    val focus = remember {
        FocusRequester()
    }
    val localFocus = LocalFocusManager.current
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .scrollable(state = logScroll, orientation = Orientation.Vertical)
    ) {
        Spacer(modifier = Modifier.height(22.dp))

        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Demeter",
            fontSize = 56.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.Cursive,
        )
        Spacer(modifier = Modifier.height(22.dp))
        Text(
            text = "Welcome back",
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
        )
        Spacer(modifier = Modifier.height(52.dp))
        Text(
            text = "Login to continue where you left off",
            fontWeight = FontWeight.Light,
            fontSize = 18.sp,
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            modifier = Modifier
                .focusRequester(focus),
            value = email,
            onValueChange = handdleEmail,
            label = { Text(text = "Username") },
            placeholder = { Text(text = "JohnDoeler") },
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

        Spacer(modifier = Modifier.height(14.dp))
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
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.width(270.dp)
        ) {
            Text(
                text = "Forgot Password?",
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Don't Have an Account? ",
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "Sign up",
                color = Color(0xFF4885B4),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable(enabled = true, onClickLabel = null, role = null, {})
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
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
        Spacer(modifier = Modifier.height(30.dp))
        Text("Sign in with Google")
        Spacer(modifier = Modifier.height(30.dp))
        Box(
            modifier = Modifier
                .size(70.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xC9D9E5E5))
                .border(1.dp, Color.Gray, RoundedCornerShape(20.dp))
                .clickable(true, null, null, onSignInClick)
                .padding(15.dp)

        ) {
            Image(
                painter = painterResource(R.drawable.google),
                contentDescription = null
            )
        }

    }
}