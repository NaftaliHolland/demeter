package com.mmust.demeter.presentation.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mmust.demeter.presentation.onboarding.OnBoardingScreen
import com.mmust.demeter.presentation.onboarding.viewmodel.OnBoardingViewModel

@Composable
fun AppNavGraph(
    startDestination: String
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Route.Onboarding.route) {
            val viewModel: OnBoardingViewModel = hiltViewModel()
            OnBoardingScreen(viewModel)
        }

        composable(route = Route.Home.route) {
            Text(text = "Home meehn")
        }
    }
}