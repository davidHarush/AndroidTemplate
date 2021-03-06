package com.david.haru.myandroidtemplate

import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Created by David Harush
 * On 21/10/2020.
 */

fun ViewModel.runCoroutine(block: suspend CoroutineScope.() -> Unit) {
    viewModelScope.launch {
        block()
    }
}

fun View.gone() {
    isVisible = false
}

fun View.visible() {
    isVisible = true
}

fun <T> T.print(tag: String = "printObj"): T {
    Log.i(tag, toString())
    return this
}

