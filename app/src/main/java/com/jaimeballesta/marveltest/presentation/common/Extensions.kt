package com.jaimeballesta.marveltest.presentation.common

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jaimeballesta.marveltest.di.API_KEY
import com.jaimeballesta.marveltest.di.PRIVATE_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.math.BigInteger
import java.security.MessageDigest

fun <T> LifecycleOwner.launchAndCollect(
    flow: Flow<T>,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    body: (T) -> Unit,
) {
    lifecycleScope.launch {
        this@launchAndCollect.repeatOnLifecycle(state) {
            flow.collect(body)
        }
    }
}

fun getCurrentMD5(ts: String): String {
    val input = ts + PRIVATE_KEY + API_KEY
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
}