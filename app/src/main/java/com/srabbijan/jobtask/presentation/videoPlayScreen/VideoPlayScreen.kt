package com.srabbijan.jobtask.presentation.videoPlayScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.srabbijan.jobtask.data.remote.dto.DemoRemoteData
import com.srabbijan.jobtask.presentation.commonComponents.customImage.NetworkImage
import com.srabbijan.jobtask.presentation.commonComponents.videoPlayer.CustomVideoPlayer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoPlayerScreen(
    data: DemoRemoteData,
    navigateUp: () -> Unit
) {
    var descriptionEnabled by remember { mutableStateOf(false) }
    var displayVideoPlayer by remember { mutableStateOf(true) }
    var isSubscribed by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
    ) {

        if (displayVideoPlayer) {
            CustomVideoPlayer(
                modifier = Modifier.height(350.dp),
                videoUrl = data?.videoUrl,
                thumbnailResId = data?.thumbnailUrl.toString(),
                isPlaying = displayVideoPlayer,
            ) {
                displayVideoPlayer = false
                navigateUp.invoke()
            }
        }
        else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(356.dp),
                contentAlignment = Alignment.Center
            ) {

                val image = data?.thumbnailUrl.toString()
                NetworkImage(
                    url = image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                )

                Box(
                    modifier = Modifier
                        .width(89.dp)
                        .height(120.dp),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(
                        onClick = {
                            displayVideoPlayer = true
                        },
                        modifier = Modifier
                            .pointerHoverIcon(icon = PointerIcon.Hand)
                            .align(alignment = Alignment.Center)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = "play icon",
                            tint = Color.White,
                            modifier = Modifier.size(300.dp)
                        )
                    }
                }

            }

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = data.title.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.weight(1f)
            )
            Icon(imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                modifier = Modifier
                    .pointerHoverIcon(icon = PointerIcon.Hand)
                    .size(24.dp)
                    .clickable {
                        descriptionEnabled = true
                    })
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val views = data.views.toString()
            val pubDate = data.uploadTime.toString()

            Text(
                text = "${views} views • ${pubDate}",
                fontSize = 12.sp
            )
        }

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp),
            thickness = 1.dp,
            color = Color.LightGray
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            data.thumbnailUrl?.let {
                rememberAsyncImagePainter(
                    it
                )
            }?.let {
                Image(
                    painter = it,
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .pointerHoverIcon(icon = PointerIcon.Hand)
                        .clickable {
                        },
                    contentScale = ContentScale.FillBounds
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement
                    .spacedBy(4.dp)
            ) {
                val channelTitle = data.author.toString()

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = channelTitle,
                        fontWeight = FontWeight.Bold,
                        fontSize = if (isSubscribed) 12.sp else MaterialTheme.typography.titleSmall.fontSize

                    )

                }
                Text(
                    text = "${data.subscriber}",
                    fontSize = 14.sp
                )

            }
            androidx.compose.animation.AnimatedVisibility(!isSubscribed) {
                Text(text = "SUBSCRIBE",
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .pointerHoverIcon(icon = PointerIcon.Hand)
                        .clickable {
                            isSubscribed = !isSubscribed
                        }
                )
            }

            AnimatedVisibility(isSubscribed) {
                Card(
                    modifier = Modifier
                        .width(145.dp)
                        .height(35.dp)
                        .padding(8.dp),
                    onClick = {
                        isSubscribed = !isSubscribed
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Notifications,
                            contentDescription = null,
                            modifier = Modifier.size(25.dp)
                        )
                        Text(
                            text = "Subscribed",
                            fontSize = MaterialTheme.typography.titleSmall.fontSize,
                            fontWeight = FontWeight.SemiBold
                        )
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowDown,
                            contentDescription = null,
                            modifier = Modifier.size(25.dp)
                        )
                    }
                }
            }
        }


        // Horizontal Divider
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp),
            thickness = 1.dp,
            color = Color.LightGray
        )
        Text(
            text = "Description",
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 4.dp)
        )
        if (descriptionEnabled) {
            ModalBottomSheet(
                onDismissRequest = {
                    descriptionEnabled = false
                },
                modifier = Modifier.fillMaxWidth(),
                sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true,
                    confirmValueChange = { true }),
                shape = RoundedCornerShape(12.dp),
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = Color.Black,
                tonalElevation = 8.dp,
                scrimColor = Color.Transparent,
                dragHandle = null,
                windowInsets = BottomSheetDefaults.windowInsets,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(state = rememberScrollState())
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Description",
                            fontWeight = FontWeight.Bold,
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(start = 4.dp)
                        )

                        IconButton(onClick = {
                            descriptionEnabled = false
                        }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = null)
                        }
                    }

                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp),
                        thickness = 2.dp,
                        color = DividerDefaults.color
                    )

                    val videoTitle = data.title.toString()
                    Text(
                        text = videoTitle,
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        maxLines = 2,
                        textAlign = TextAlign.Justify,
                        overflow = TextOverflow.Ellipsis,
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 2.dp, top = 4.dp, bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        // Channel Image
                        NetworkImage(
                            modifier = Modifier
                                .size(15.dp)
                                .clip(CircleShape)
                                .clickable {

                                },
                            url = data?.thumbnailUrl.toString(),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        data?.title?.let {
                            Text(
                                text = it,
                                fontWeight = FontWeight.Bold,
                                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                                modifier = Modifier.fillMaxWidth(),
                                maxLines = 1,
                                textAlign = TextAlign.Justify,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }

                    }

                    //data Details
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp, start = 60.dp, end = 60.dp, bottom = 20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "100",
                                fontWeight = FontWeight.Bold,
                                fontSize = MaterialTheme.typography.titleMedium.fontSize
                            )

                            Text(
                                text = "Likes",
                                fontWeight = FontWeight.Normal,
                                fontSize = MaterialTheme.typography.labelSmall.fontSize
                            )
                        }

                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "100",
                                fontWeight = FontWeight.Bold,
                                fontSize = MaterialTheme.typography.titleMedium.fontSize
                            )

                            Text(
                                text = "Views",
                                fontWeight = FontWeight.Normal,
                                fontSize = MaterialTheme.typography.labelSmall.fontSize
                            )
                        }

                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Text(
                                text = "${data?.uploadTime}",
                                fontWeight = FontWeight.Bold,
                                fontSize = MaterialTheme.typography.titleMedium.fontSize
                            )
                        }
                    }

                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        thickness = 2.dp,
                        color = DividerDefaults.color
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        var desc_expanded by remember { mutableStateOf(false) }
                        val videoDescription = data?.description.toString()
                        Text(
                            text = videoDescription,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(top = 16.dp, start = 4.dp, end = 4.dp),
                            maxLines = if (desc_expanded) 40 else 9,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = MaterialTheme.typography.bodySmall.fontSize
                        )

                        Text(
                            text = if (desc_expanded) "less" else "more",
                            fontSize = MaterialTheme.typography.bodySmall.fontSize,
                            modifier = Modifier
                                .clickable {
                                    desc_expanded = !desc_expanded
                                }
                                .align(alignment = Alignment.Bottom)
                        )
                    }

                }
            }
        }


    }
}