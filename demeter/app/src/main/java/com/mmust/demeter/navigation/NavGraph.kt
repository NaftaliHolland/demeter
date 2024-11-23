package com.mmust.demeter.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mmust.demeter.ViewModels.Auth.AuthViewModel
import com.mmust.demeter.Views.Auth.Auth
import com.mmust.demeter.Views.GreenHouseScreen
import com.mmust.demeter.Views.HomeScreen
import com.mmust.demeter.Views.NewGreenHouse.Add
import com.mmust.demeter.Views.Profile.Profile

object Routes {
    const val HOME = "home"
    const val PROFILE = "profile"
    const val GREEN_HOUSE = "greenHouse"
    const val AUTH = "auth"
    const val ADD_GREEN_HOUSE = "addGreenHouse"
}

@Composable
fun NavGraph(navController: NavHostController, paddingValues: PaddingValues,vm: AuthViewModel) {
    NavHost(
        navController = navController,
        startDestination = Routes.AUTH
    ) {
        composable(route = Routes.AUTH) {
           Auth(navController,vm)
        }
        composable(route = Routes.HOME) {
            HomeScreen(navController)
        }
        composable(route = Routes.PROFILE) {
            Profile(
                userData = vm.getSignedInUser(),
                logout = {
                    vm.signOut()
                    navController.navigate(Routes.AUTH) {
                        popUpTo(Routes.PROFILE)
                    }
                }
            )
        }
        composable(route = Routes.GREEN_HOUSE) {
           GreenHouseScreen(paddingValues = paddingValues)
        }
        composable(route = Routes.ADD_GREEN_HOUSE) {
            Add(user = vm.getSignedInUser()!!)
        }
    }
}