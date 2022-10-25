package com.example.countdown_timer_view.utils

import android.content.res.Resources
import kotlinx.coroutines.*

val Int.dp: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()

internal fun CoroutineScope.countDownTimer(
    totalMillis: Long,
    intervalInMillis: Long = 1000,
    onInterval: (millisLeft: Long) -> Unit = {},
    onFinished: () -> Unit = {},
    onPaused: () -> Boolean = { false }
) = this.launch(Dispatchers.IO) {
    var total = totalMillis
    while (isActive) {
        if (!onPaused.invoke()) {
            if (total > 0) {
                withContext(Dispatchers.Main) {
                    onInterval(total)
                }
                delay(intervalInMillis)
                total -= intervalInMillis
            } else {
                withContext(Dispatchers.Main) {
                    onFinished()
                    cancel("Task Completed")
                }
            }
        }
    }
}

fun Long.milliSecondsToCountDown(): Triple<Long, Long, Long> {
    val seconds = this / 1000
    val hour = seconds / 3600
    val min = (seconds / 60) % 60
    val sec = seconds % 60
    return Triple(hour, min, sec)
}