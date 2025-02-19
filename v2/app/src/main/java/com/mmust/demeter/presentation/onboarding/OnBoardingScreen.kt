package com.mmust.demeter.presentation.onboarding

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mmust.demeter.presentation.Dimens.mediumPadding1
import com.mmust.demeter.presentation.Dimens.pageIndicatorWidth
import com.mmust.demeter.presentation.common.MainButton
import com.mmust.demeter.presentation.common.MainTextButton
import com.mmust.demeter.presentation.navigation.Route
import com.mmust.demeter.presentation.onboarding.components.OnBoardingPage
import com.mmust.demeter.presentation.onboarding.components.PageIndicator
import com.mmust.demeter.presentation.onboarding.viewmodel.OnBoardingViewModel
import com.mmust.demeter.ui.theme.DemeterTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    viewModel: OnBoardingViewModel = hiltViewModel(),
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        val pagerState = rememberPagerState(initialPage = 0) {
            pages.size
        }
        LaunchedEffect(pagerState.currentPage) {
            Log.d("Onbaording Screen", "Current page: ${pagerState.currentPage}")
        }
        val buttonState = remember {
           derivedStateOf {
              when(pagerState.currentPage) {
                  0 -> listOf("", "Next")
                  1 -> listOf("Back", "Next")
                  2 -> listOf("Back", "Get Started")
                  else -> listOf("", "")
              }
           }
        }
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.9f),
            state = pagerState) { index ->
            OnBoardingPage(page = pages[index])
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PageIndicator(
                modifier = Modifier.width(52.dp),
                pageSize = pages.size,
                selectedPage = pagerState.currentPage
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                val scope = rememberCoroutineScope()

                if (buttonState.value[0].isNotEmpty()) {
                    MainTextButton(
                        text = buttonState.value[0],
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(page = pagerState.currentPage - 1)
                            }
                        }
                    )
                }

                MainButton(
                    text = buttonState.value[1],
                    onClick = {
                        scope.launch {
                            if (pagerState.currentPage == 2) {
                                viewModel.saveOnboardingCompletion()
                                navController.navigate(Route.Home.route)
                            } else {
                               pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
                            }
                        }
                    }
                )
            }
        }
        Spacer(modifier = Modifier.weight(0.02f))
    }
}

@Preview(showBackground = true)
@Composable
fun OnBoardingScreenPreview() {
    DemeterTheme {
        Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
        }
    }
}