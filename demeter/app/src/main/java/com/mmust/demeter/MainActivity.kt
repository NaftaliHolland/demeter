package com.mmust.demeter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.mmust.demeter.ViewModels.Auth.AuthViewModel
import com.mmust.demeter.ui.composables.MainScaffold
import com.mmust.demeter.ui.theme.DemeterTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DemeterTheme {
                val navController = rememberNavController()
                val vm = AuthViewModel(applicationContext)

                MainScaffold(navController = navController, vm = vm)
            }
        }
    }
}