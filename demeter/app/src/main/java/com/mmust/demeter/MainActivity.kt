package com.mmust.demeter

import SignInViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.mmust.demeter.ui.composables.MainScaffold
import com.mmust.demeter.ui.theme.DemeterTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val corutineScope = rememberCoroutineScope()
            val navController = rememberNavController()
            var route by remember {
                mutableStateOf("auth")
            }
            DemeterTheme {
                val navController = rememberNavController()
                val signInViewModel = SignInViewModel(applicationContext)

                MainScaffold(navController = navController, signInViewModel = signInViewModel)
            }
        }
    }
}