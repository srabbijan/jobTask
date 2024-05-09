package com.srabbijan.jobtask.utils

/*
return 1000 -> 1k
 */
fun String?.formatViewViews(): String {
    if (this.isNullOrEmpty()) return "0"
    val number = this.replace(",", "").toDouble()
    return when {
        number >= 1_000_000 -> String.format("%.2fM", number / 1_000_000)
        number >= 1_000 -> String.format("%.2fk", number / 1_000)
        else -> number.toInt().toString()
    }
}