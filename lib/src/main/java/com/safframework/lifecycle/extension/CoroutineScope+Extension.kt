package com.safframework.lifecycle.extension

import androidx.lifecycle.LifecycleOwner
import com.safframework.kotlin.coroutines.uiScope
import com.safframework.lifecycle.listener.LifecycleCoroutineListener
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 *
 * @FileName:
 *          com.safframework.lifecycle.extension.`CoroutineScope+Extension`
 * @author: Tony Shen
 * @date: 2020-01-24 16:48
 * @version: V1.0 <描述当前版本功能>
 */
fun <T> CoroutineScope.asyncWithLifecycle(lifecycleOwner: LifecycleOwner,
                                       context: CoroutineContext = EmptyCoroutineContext,
                                       start: CoroutineStart = CoroutineStart.DEFAULT,
                                       block: suspend CoroutineScope.() -> T): Deferred<T> {

    val deferred = uiScope().async(context, start) {

        block()
    }

    lifecycleOwner.lifecycle.addObserver(LifecycleCoroutineListener(deferred))

    return deferred
}

fun <T> CoroutineScope.bindWithLifecycle(lifecycleOwner: LifecycleOwner,
                                      block: CoroutineScope.() -> Deferred<T>): Deferred<T> {

    val deferred = block.invoke(this)

    lifecycleOwner.lifecycle.addObserver(LifecycleCoroutineListener(deferred))

    return deferred
}