package com.srabbijan.jobtask.presentation.commonComponents.customImage

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.srabbijan.jobtask.R

@Composable
fun NetworkImage(
    modifier: Modifier,
    url: String,
    contentDescription: String?,
    contentScale: ContentScale
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .placeholder(R.drawable.place_holder)
            .error(R.drawable.place_holder)
            .build(),
        contentDescription = contentDescription,
        modifier = modifier.fillMaxSize(),
        contentScale = contentScale,

    )
}