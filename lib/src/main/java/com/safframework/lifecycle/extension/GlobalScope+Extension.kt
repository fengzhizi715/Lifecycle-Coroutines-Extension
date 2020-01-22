package com.safframework.lifecycle.extension

import androidx.lifecycle.LifecycleOwner
import com.safframework.lifecycle.listener.LifecycleCoroutineListener
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 *
 * @FileName:
 *          com.safframework.lifecycle.`GlobalScope+Extension`
 * @author: Tony Shen
 * @date: 2019-08-24 18:54
 * @version: V1.0 <描述当前版本功能>
 */
fun <T> GlobalScope.asyncWithLifecycle(lifecycleOwner: LifecycleOwner,
                                       context: CoroutineContext = EmptyCoroutineContext,
                                       start: CoroutineStart = CoroutineStart.DEFAULT,
                                       block: suspend CoroutineScope.() -> T): Deferred<T> {

    val deferred = GlobalScope.async(context, start) {

        block()
    }

    lifecycleOwner.lifecycle.addObserver(LifecycleCoroutineListener(deferred))

    return deferred
}

fun <T> GlobalScope.bindWithLifecycle(lifecycleOwner: LifecycleOwner,
                                             block: GlobalScope.() -> Deferred<T>): Deferred<T> {

    val deferred = block.invoke(this)

    lifecycleOwner.lifecycle.addObserver(LifecycleCoroutineListener(deferred))

    return deferred
}