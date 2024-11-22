@file:OptIn(ExperimentalMaterial3Api::class)

package com.mmust.demeter.ui.composables

import SignInViewModel
import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mmust.demeter.navigation.NavGraph
import com.mmust.demeter.navigation.Routes

@Composable
fun MainScaffold(navController: NavHostController, signInViewModel: SignInViewModel) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            if (currentRoute != Routes.AUTH) {
                TopAppBar(title = { Text("My App") })
            }
        },
        bottomBar = {
            if (currentRoute != Routes.AUTH) {
                BottomBar(navController)
            }
        }
    ) { innerPadding ->
        val context = LocalContext.current
        val state by signInViewModel.state.collectAsStateWithLifecycle()

        LaunchedEffect(key1 = state.isSignInSuccessful) {
            if (state.isSignInSuccessful) {
                Toast.makeText(
                    context,
                    "Sign in Sucessful",
                    Toast.LENGTH_LONG
                ).show()
                navController.navigate(Routes.HOME) {
                    popUpTo(Routes.AUTH) {
                        inclusive = true
                    }
                }
            }
            signInViewModel.resetState()
        }
        NavGraph(navController = navController, paddingValues = innerPadding)
    }

}