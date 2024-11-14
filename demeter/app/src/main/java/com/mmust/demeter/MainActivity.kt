package com.mmust.demeter

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import com.mmust.demeter.ViewModels.Auth.AuthViewModel
import com.mmust.demeter.Models.Auth.GoogleAuthUiClient
import com.mmust.demeter.Views.Auth.AuthPage
import com.mmust.demeter.Views.Profile.Profile
import com.mmust.demeter.Views.Routes.MainRoutes
import com.mmust.demeter.ui.theme.DemeterTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            onTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val corutineScope = rememberCoroutineScope()
            val navController = rememberNavController()
            DemeterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                    ) {

                        val vm = viewModel<AuthViewModel>()
                        val state by vm.state.collectAsStateWithLifecycle()

                        val signInLauncher = rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.StartIntentSenderForResult(),
                            onResult = { result ->
                                if (result.resultCode == RESULT_OK) {
                                    corutineScope.launch {
                                        val signInResult =
                                            googleAuthUiClient.signInWithIntent(
                                                intent = result.data ?: return@launch
                                            )
                                        vm.onSignInResult(signInResult)
                                    }
                                }
                            }
                        )
                        LaunchedEffect(key1 = state.isSignInSuccessful) {
                            if (state.isSignInSuccessful) {
                                Toast.makeText(
                                    applicationContext,
                                    "Sign in Sucessful",
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
                            composable(MainRoutes.Auth.route) {
                                AuthPage(state = state, onSignInClick = {
                                    corutineScope.launch {
                                        val signInIntentSender = googleAuthUiClient.signIn()
                                        signInLauncher.launch(
                                            IntentSenderRequest.Builder(
                                                signInIntentSender ?: return@launch
                                            ).build()

                                        )

                                    }
                                })
                            }
                            composable(MainRoutes.Home.route) {
                                Column {
                                    Text(text = "Sasa ni Home")
                                    Button(onClick = { navController.navigate(MainRoutes.Profile.route) }) {
                                        Text(
                                            text = "Profile"
                                        )
                                    }
                                }

                            }
                            composable(MainRoutes.Profile.route) {
                                Profile(userData = googleAuthUiClient.getSignedInUser()) {
                                    corutineScope.launch {
                                        googleAuthUiClient.signout()
                                        Toast.makeText(
                                            applicationContext,
                                            "Signed out",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        navController.navigate(MainRoutes.Auth.route) {
                                            popUpTo(MainRoutes.Auth.route) {
                                                inclusive = false
                                            }
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}
