package com.mmust.demeter

import SignInViewModel
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mmust.demeter.Views.Auth.AuthPage
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
            var route by remember {
                mutableStateOf("auth")
            }
            DemeterTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { if (route != MainRoutes.Auth.route) BottomBar(navController) }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                    ) {

                        val vm = SignInViewModel(applicationContext)
                        val state by vm.state.collectAsStateWithLifecycle()

                        LaunchedEffect(key1 = state.isSignInSuccessful) {
                            if (state.isSignInSuccessful) {
                                Toast.makeText(
                                    applicationContext,
                                    "Sign in Successful",
                                    Toast.LENGTH_LONG
                                ).show()
                                navController.navigate(MainRoutes.Home.route) {
                                    popUpTo(MainRoutes.Auth.route) {
                                        inclusive = true
                                    }
                                }
                            }

                            vm.resetState()
                        }
                        NavHost(
                            navController = navController,
                            startDestination = MainRoutes.Auth.route
                        ) {
                            route = navController.currentDestination?.route.toString()
                            composable(MainRoutes.Auth.route) {
                                AuthPage(navController)
                            }
                            composable(MainRoutes.Home.route) {
                                route = MainRoutes.Home.route
                                HomeScreen()

                            }
                            composable(MainRoutes.Profile.route) {
                                Profile(
                                    userData = vm.getSignedInUser(),
                                    logout = {}
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

