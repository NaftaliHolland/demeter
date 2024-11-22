package com.mmust.demeter.Views.Auth


import com.mmust.demeter.ViewModels.Auth.AuthState
import com.mmust.demeter.ViewModels.Auth.AuthViewModel
import android.widget.Toast
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
import com.mmust.demeter.Views.Routes.MainRoutes


@Composable
fun SignUp(navigate:NavController, signInViewModel: AuthViewModel, authNavController: NavController){
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var pwd by remember { mutableStateOf("") }
    var cpwd by remember { mutableStateOf("") }
    var mess by remember { mutableStateOf("") }
    val handleEmail = {it:String ->
        email = it
    }
    val handlePwd = {it : String ->
        pwd = it
    }
    val handleCpwd = {it:String ->
        cpwd = it
        if(cpwd != pwd){
            mess = "Passwords do not match"
        }else{
            mess = ""
        }
    }
    val focus = remember {
        FocusRequester()
    }
    val authState = signInViewModel.authState.observeAsState()
    val localFocus = LocalFocusManager.current
    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Authorised -> navigate.navigate(MainRoutes.Home.route)
            is AuthState.Error -> Toast.makeText(context,
                (authState.value as AuthState.Error).msg,Toast.LENGTH_LONG).show()
            else -> Unit
        }

    }
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
                text = "Sign up",
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
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        localFocus.moveFocus(FocusDirection.Down)
                    }
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = cpwd,
                onValueChange = handleCpwd,
                label = { Text(text = "Confirm Password") },
                placeholder = { Text(text = "850_.@23/") },
                maxLines = 1,
                leadingIcon = { Icon(imageVector = Icons.Outlined.Lock, contentDescription = "") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = if(pwd == cpwd) ImeAction.Done else ImeAction.None
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (email.isNotEmpty() && pwd.isNotEmpty() && cpwd.isNotEmpty() && (pwd == cpwd))
                            signInViewModel.signInWithEmail(email, pwd, context)
                        else
                            focus.requestFocus()
                    }
                )
            )
            Row {
                Text(mess, color = Color.Red, fontWeight = FontWeight.Light, fontSize = 16.sp)
            }

        }
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row {
                Text(text = "Already have an account ?  ")
                Text(
                    text = "Log in",
                    color = Color(0xFF4885B4),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clickable(enabled = true, onClickLabel = null, role = null, {
                            authNavController.navigate("login")
                        })
                )
            }
            Button(
                contentPadding = PaddingValues(105.dp, 17.dp),
                onClick = {
                    if (email.isNotEmpty() && pwd.isNotEmpty() && cpwd.isNotEmpty() && (pwd == cpwd))
                        signInViewModel.signInWithEmail(email, pwd, context)
                    else
                        focus.requestFocus()
                },
                colors = ButtonColors(
                    containerColor = Color(0xFF4885B4),
                    contentColor = Color.Black,
                    disabledContentColor = Color.Black,
                    disabledContainerColor = Color.LightGray
                ),
                enabled = if (email.isNotEmpty() && pwd.isNotEmpty() && cpwd.isNotEmpty() && (pwd == cpwd)){
                    true
                }else{
                    false
                }
            ) {
                Text(text = "Sign Up", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color(0xC9D9E5E5))
                    .border(1.dp, Color.LightGray, CircleShape)
                    .clickable { signInViewModel.signInWithGoogle(context = context,navigate) }
                    .padding(8.dp, 5.dp)

            ) {
                Image(modifier = Modifier.size(50.dp),painter = painterResource(R.drawable.google), contentDescription = null)
                Text(text = "Sign in with Google")
            }
        }

    }
}
