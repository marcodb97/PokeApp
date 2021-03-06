package com.marcodallaba.pokeapp.utils

import android.os.SystemClock
import android.view.View

/**
 * Prevent double clicks on Button or other [View] types.
 */
class SafeClickListener(
    private val intervalMillis: Int = 1000,
    private val onSafeCLick: (View) -> Unit
) : View.OnClickListener {
    private var lastTimeClicked: Long = 0
    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < intervalMillis) {
            return
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        onSafeCLick(v)
    }
}

fun View.setSafeOnClickListener(intervalMillis: Int = 1000, onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener(intervalMillis) {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}
