package com.mmust.demeter.presentation.navigation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.navigation
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mmust.demeter.data.source.SessionManager
import com.mmust.demeter.presentation.MainViewModel
import com.mmust.demeter.presentation.alerts.NotificationsScreen
import com.mmust.demeter.presentation.alerts.NotificationsViewModel
import com.mmust.demeter.presentation.common.AppScaffold
import com.mmust.demeter.presentation.common.DefaultTopBar
import com.mmust.demeter.presentation.greenhouse.GreenhouseDetailsScreen
import com.mmust.demeter.presentation.greenhouse.GreenhouseDetailsViewModel
import com.mmust.demeter.presentation.greenhouse.WebSocketViewModel
import com.mmust.demeter.presentation.home.GreenhouseViewModel
import com.mmust.demeter.presentation.home.HomeScreen
import com.mmust.demeter.presentation.login.LoginScreen
import com.mmust.demeter.presentation.login.LoginViewModel
import com.mmust.demeter.presentation.onboarding.OnBoardingScreen
import com.mmust.demeter.presentation.onboarding.viewmodel.OnBoardingViewModel
import com.mmust.demeter.presentation.signUp.SignUpScreen
import com.mmust.demeter.presentation.signUp.SignUpViewModel

@Composable
fun AuthNavGraph() {
    val navController = rememberNavController()
    val viewModel: OnBoardingViewModel = hiltViewModel()
    val mainViewModel: MainViewModel = hiltViewModel()
    val loginViewModel: LoginViewModel = hiltViewModel()
    val signUpViewModel: SignUpViewModel = hiltViewModel()
    var startDestination by remember { mutableStateOf(Route.Onboarding.route)}
    val isLoggedIn by mainViewModel.isLoggedIn.collectAsState()

    if (isLoggedIn != null) {
        NavHost(
            navController = navController,
            startDestination = if (isLoggedIn == true) Route.Home.route else Route.Onboarding.route
        ) {
            composable(route = Route.Onboarding.route) {
                OnBoardingScreen(viewModel = viewModel, navController = navController)
            }
            composable(route = Route.Login.route) {
                LoginScreen(loginViewModel, navController)
            }
            composable(route = Route.SignUp.route) {
                SignUpScreen(signUpViewModel, navController)
            }
            composable(route = Route.Home.route) {
                AppNavGraph()
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

}

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val greenhouseViewModel: GreenhouseViewModel = hiltViewModel()
    val notificationsViewModel: NotificationsViewModel = NotificationsViewModel()

    NavHost(
        navController = navController,
        startDestination = Route.Home.route
    ) {
        composable(route = Route.Settings.route ) {
            AppScaffold(
                navController = navController,
                currentDestination = currentDestination,
                topBar = { DefaultTopBar(navController) }
            ) {
               Text("Settings Screen")
            }
        }
        composable(route = Route.Home.route) {
            AppScaffold(
                navController = navController,
                currentDestination = currentDestination,
            ) {
                HomeScreen(viewModel = greenhouseViewModel, navController = navController)
            }
        }
        composable(route = Route.Alert.route) {
           NotificationsScreen(notificationsViewModel)
        }
        /*composable(route = Route.Settings.route) {
            AppScaffold(
                navController = navController,
                currentDestination = currentDestination,
                topBar = { DefaultTopBar(navController) }
            ) {
                Text(text = "Settings meehn")
            }
        }*/
        composable(Route.Greenhouse.route) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?: ""
            val greenhouseDetailsViewModel: GreenhouseDetailsViewModel = hiltViewModel()
            val webSocketViewModel: WebSocketViewModel = hiltViewModel()
            AppScaffold(
               navController = navController,
                currentDestination = currentDestination,
                topBar = { DefaultTopBar(navController) }
            ) {
                GreenhouseDetailsScreen(
                    greenhouseDetailsViewModel = greenhouseDetailsViewModel,
                    webSocketViewModel = webSocketViewModel,
                )
            }
        }
    }
}