package com.srabbijan.jobtask.presentation.commonComponents

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ErrorScreen(errorMsg: String, modifier: Modifier = Modifier) {
    Text(text = "errror $errorMsg")
}