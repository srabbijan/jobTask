package com.srabbijan.jobtask.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun isConnected(): Boolean {
    val context = LocalContext.current
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val isConnected = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    } else {
        @Suppress("DEPRECATION")
        val networkInfo = connectivityManager.activeNetworkInfo
        networkInfo?.isConnected == true
    }

    return isConnected
}