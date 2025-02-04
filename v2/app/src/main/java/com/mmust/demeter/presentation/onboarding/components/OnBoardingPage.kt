package com.mmust.demeter.presentation.onboarding.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.mmust.demeter.R
import com.mmust.demeter.presentation.onboarding.Page
import com.mmust.demeter.presentation.Dimens.mediumPadding1
import com.mmust.demeter.presentation.onboarding.pages
import com.mmust.demeter.ui.theme.DemeterTheme

@Composable
fun OnBoardingPage(
    modifier: Modifier = Modifier,
    page: Page
) {
    Column(modifier = Modifier.fillMaxHeight()) {
        Image(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(fraction = 0.6f),
            painter = painterResource(id = page.image),
            contentDescription="null",
            contentScale= ContentScale.Crop
        )
    Spacer(modifier = Modifier.height(mediumPadding1))
    Text(
        text = page.title,
        modifier = Modifier.padding(horizontal = mediumPadding1),
        style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
        color = colorResource(id = R.color.black)
    )
    Text(
        text = page.description,
        modifier = Modifier.padding(horizontal = mediumPadding1),
        style = MaterialTheme.typography.bodyMedium,
        color = colorResource(id = R.color.black)
    )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode= UI_MODE_NIGHT_YES)
@Composable
fun OnBoardingPagePreview() {
    DemeterTheme {
        OnBoardingPage(page = pages[0])
    }
}