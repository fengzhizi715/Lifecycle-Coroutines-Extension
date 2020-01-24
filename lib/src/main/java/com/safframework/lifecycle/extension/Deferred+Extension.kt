package com.safframework.lifecycle.extension

import android.util.Log
import com.safframework.kotlin.coroutines.uiScope
import kotlinx.coroutines.*

/**
 *
 * @FileName:
 *          com.safframework.lifecycle.`Deferred+Extension`
 * @author: Tony Shen
 * @date: 2019-08-24 18:55
 * @version: V1.0 <描述当前版本功能>
 */

infix fun <T> Deferred<T>.then(block: (T) -> Unit) = uiScope().launch {

    block(this@then.await())
}

infix fun <T, R> Deferred<T>.thenAsync(block: (T) -> R)= uiScope().async {

    block(this@thenAsync.await())
}

/**
 * 如果遇到超时或者异常，则返回null。
 */
suspend fun <T> Deferred<T>.awaitOrNull(timeout: Long = 0L) = try {

    if (timeout > 0) {

        withTimeout(timeout) {

            this@awaitOrNull.await()
        }

    } else {

        this.await()
    }
} catch (e: Exception) {

    Log.e("Deferred", e.message)
    null
}