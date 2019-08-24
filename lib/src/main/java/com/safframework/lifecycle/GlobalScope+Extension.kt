package com.safframework.lifecycle

import android.arch.lifecycle.LifecycleOwner
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

val UI: CoroutineDispatcher = Dispatchers.Main
val IO: CoroutineDispatcher = Dispatchers.IO

fun runOnUI(block: suspend CoroutineScope.() -> Unit): Job = GlobalScope.launch(context = UI, block = block)

fun <T> runOnUI(block: suspend CoroutineScope.() -> T): Deferred<T> = GlobalScope.async(context = UI, block = block)

fun runInBackground(block: suspend CoroutineScope.() -> Unit): Job = GlobalScope.launch(context = IO, block = block)

fun <T> runInBackground(block: suspend CoroutineScope.() -> T): Deferred<T> = GlobalScope.async(context = IO, block = block)

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
                                      block: CoroutineScope.() -> Deferred<T>): Deferred<T> {

    val deferred = block.invoke(this)

    lifecycleOwner.lifecycle.addObserver(LifecycleCoroutineListener(deferred))

    return deferred
}