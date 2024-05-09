package com.srabbijan.jobtask.utils

/*
return 1000 -> 1k
 */
fun String?.formatViewCount(): String {
    if (this.isNullOrEmpty()) return "0"
    val number = this.replace(",", "").toDouble()
    return when {
        number >= 1_000_000_000 -> "${(number / 1_000_000_000).toInt()}B"
        number >= 1_000_000 -> "${(number / 1_000_000).toInt()}M"
        number >= 1_000 -> "${(number / 1_000).toInt()}K"
        else -> "$number"
    }
}