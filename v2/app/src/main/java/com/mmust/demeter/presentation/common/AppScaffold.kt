package com.mmust.demeter.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
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
        topBar = topBar?: {},
        contentWindowInsets = WindowInsets.systemBars,
        bottomBar = { BottomNavBar(navController, currentDestination) },
    ) {paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            content(paddingValues)
        }
    }
}