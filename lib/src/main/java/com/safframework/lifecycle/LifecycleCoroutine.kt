package com.safframework.lifecycle

import android.arch.lifecycle.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 *
 * @FileName:
 *          com.safframework.lifecycle.LifecycleCoroutine
 * @author: Tony Shen
 * @date: 2018-12-05 14:02
 * @version: V1.0 <描述当前版本功能>
 */
class LifecycleCoroutineListener(private val job: Job,
                                 private val cancelEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY) : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pause() = handleEvent(Lifecycle.Event.ON_PAUSE)

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop() = handleEvent(Lifecycle.Event.ON_STOP)

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroy() = handleEvent(Lifecycle.Event.ON_DESTROY)

    private fun handleEvent(e: Lifecycle.Event) {

        if (e == cancelEvent && !job.isCancelled) {
            job.cancel()
        }
    }
}

fun <T> GlobalScope.asyncWithLifecycle(lifecycleOwner: LifecycleOwner,
                                       context: CoroutineContext = EmptyCoroutineContext,
                                       start: CoroutineStart = CoroutineStart.DEFAULT,
                                       block: suspend CoroutineScope.() -> T): Deferred<T> {

    val deferred = GlobalScope.async(context,start) {

        return@async block()
    }

    lifecycleOwner.lifecycle.addObserver(LifecycleCoroutineListener(deferred))

    return deferred
}

inline fun <T> GlobalScope.bindWithLifecycle(lifecycleOwner: LifecycleOwner,
                                         block: CoroutineScope.() -> Deferred<T>): Deferred<T> {
    val job = block.invoke(this)

    lifecycleOwner.lifecycle.addObserver(LifecycleCoroutineListener(job))

    return job
}