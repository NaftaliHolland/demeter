package com.mmust.demeter

import AuthState
import AuthViewModel
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mmust.demeter.Views.Auth.Auth
import com.mmust.demeter.Views.Auth.Login
import com.mmust.demeter.Views.HomeScreen
import com.mmust.demeter.Views.Profile.Profile
import com.mmust.demeter.Views.Routes.MainRoutes
import com.mmust.demeter.ui.composables.BottomBar
import com.mmust.demeter.ui.theme.DemeterTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            rememberCoroutineScope()
            val navController = rememberNavController()
            val vm = AuthViewModel(applicationContext)
            val authState = vm.authState.observeAsState()
            LaunchedEffect(authState.value) {
                when(authState.value){
                    is AuthState.Unauthorised -> navController.navigate(MainRoutes.Auth.route)
                    is AuthState.Authorised -> navController.navigate(MainRoutes.Home.route)
                    is AuthState.Error -> Toast.makeText(applicationContext,
                        (authState.value as AuthState.Error).msg, Toast.LENGTH_LONG).show()
                    else -> Unit
                }

            }
            DemeterTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { if (authState.value is AuthState.Authorised) BottomBar(navController) }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                    ) {


                        NavHost(
                            navController = navController,
                            startDestination = MainRoutes.Auth.route
                        ) {
                            composable(MainRoutes.Auth.route) {
                                Auth(navController,vm)
                            }
                            composable(MainRoutes.Home.route) {
                                HomeScreen()

                            }
                            composable(MainRoutes.Profile.route) {
                                Profile(
                                    userData = vm.getSignedInUser(),
                                    logout = { vm.signOut() }
                                )
                            }
                            composable(
                                MainRoutes.AddGreenhouse.route
                            ) { }

                        }
                    }
                }
            }
        }
    }
}

