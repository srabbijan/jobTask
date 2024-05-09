package com.srabbijan.jobtask.presentation.commonComponents.videoPlayer

import android.os.Build
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.srabbijan.jobtask.presentation.commonComponents.customImage.NetworkImage

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(UnstableApi::class)
@Composable
fun CustomVideoPlayer(
    modifier: Modifier = Modifier,
    videoUrl: String?,
    thumbnailResId: String,
    isPlaying: Boolean,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val exoPlayer = remember { ExoPlayer.Builder(context).build() }
    var isVideoLoading by remember { mutableStateOf(true) }

    DisposableEffect(Unit) {
        // Set media source when the component is first composed
        if (videoUrl != null) {
            val mediaItem = MediaItem.fromUri(videoUrl)
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
        }

        // Dispose the ExoPlayer when the component is no longer in use
        onDispose {
            exoPlayer.release()
        }
    }

    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            // Start playback when isPlaying is true
            exoPlayer.play()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable {

            }
    ) {
        // Display the thumbnail and play icon
        if (!isPlaying) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black)
            ) {

                NetworkImage(modifier = Modifier, url = thumbnailResId, contentDescription = null, contentScale =ContentScale.Crop )
                // Display play icon in the center
                IconButton(
                    onClick = {

                    },
                    modifier = Modifier
                        .align(Alignment.Center)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        } else {
            // Display video player
            AndroidView(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black),
                factory = { context ->
                    PlayerView(context).apply {
                        player = exoPlayer
                        // Add any additional settings here if needed
                        hideController() // Manually hide the controller
                        setShowNextButton(true)
                        setShowBuffering(PlayerView.SHOW_BUFFERING_ALWAYS)
                        setControllerHideDuringAds(true)
                        setShowFastForwardButton(true)
                        setShowPreviousButton(true)
                        setBackgroundColor(0XFF000000.toInt())
                        exoPlayer.addListener(object : Player.Listener {
                            override fun onIsLoadingChanged(isLoading: Boolean) {
                                isVideoLoading = isLoading
                            }

                            override fun onIsPlayingChanged(isPlaying: Boolean) {
                                isVideoLoading = !isPlaying
                            }

                            override fun onPlayerError(error: PlaybackException) {
                                Log.e("ExoPlayer", "Error during playback. Video URL: $videoUrl", error)
                            }
                        })
                    }
                },
                update = {
                    // You can add any additional update logic if needed
                }
            )
        }
        IconButton(
            onClick = {
                onBackClick()
            },
            modifier = Modifier
                .padding(end = 8.dp)
                .align(alignment = Alignment.TopStart)
                .clip(CircleShape)
                .background(Color(0x40000000))
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = null,
                tint = Color.White
            )
        }

        // Display circular progress bar while loading video
        if (isVideoLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center),
                color = Color.White
            )
        }
    }
}




