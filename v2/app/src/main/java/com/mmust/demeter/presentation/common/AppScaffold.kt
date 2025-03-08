package com.mmust.demeter.presentation.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination

@Composable
fun AppScaffold(
    navController: NavController,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
    topBar: @Composable (() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit
    ) {
    Scaffold(
        modifier = modifier,
        topBar = topBar?: { DefaultTopBar() },
        bottomBar = { BottomNavBar(navController, currentDestination) },
        content = content
    )
}