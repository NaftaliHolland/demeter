package com.mmust.demeter.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LoginScreen(loginViewModel: LoginViewModel = hiltViewModel()) {
    val uiState by loginViewModel.uiState.collectAsState()
    var email by remember{ mutableStateOf("")}
    var password by remember{ mutableStateOf("")}
    val handleEmailChange = {it:String ->
        email = it
    }
    val handlePasswordChange = {it : String ->
        password = it
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = handleEmailChange,
            label = { Text(text = "Email") },
            placeholder = { Text(text = "youremail@gmail.com") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Email,
                    contentDescription = ""
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions (
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email
            )
        )

        OutlinedTextField(
            value = password,
            onValueChange = handlePasswordChange,
            label = { Text(text = "Password") },
            placeholder = { Text(text = "2kjs88@") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Lock,
                    contentDescription = ""
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions (
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
        )

        Button(
            onClick = {
                loginViewModel.login(email, password)
            },
            enabled = (email.isNotEmpty() && password.isNotEmpty() && !uiState.isLoading)
        ) {
            Text(text = "Login")
        }

        if (uiState.isLoading) {
            CircularProgressIndicator()
        }
        uiState.user?.let { user ->
            Text("Logged in as ${user.email}")
        }
        uiState.error?.let { error ->
            Text(error, color = MaterialTheme.colorScheme.error)
        }
    }
}