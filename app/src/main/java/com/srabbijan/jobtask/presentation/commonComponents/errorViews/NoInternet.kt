package com.srabbijan.jobtask.presentation.commonComponents.errorViews

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.srabbijan.jobtask.R
import com.srabbijan.jobtask.presentation.commonComponents.buttons.GradientButton


@Composable
fun NoInternet(
    buttonText: String = "Retry",
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.no_internet),
                contentDescription = "No Internet",
                modifier = Modifier.size(300.dp),
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = "Oops, looks like you're not connected to the internet right now.",
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                color =  Color.Black,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            val cornerRadius = 16.dp
            val gradientColor = listOf(Color(0xFFff669f), Color(0xFFff8961))
            GradientButton(
                gradientColors = gradientColor,
                cornerRadius = cornerRadius,
                nameButton = buttonText,
                roundedCornerShape = RoundedCornerShape(20.dp)
            ) {
                onRetry.invoke()
            }
        }
    }
}