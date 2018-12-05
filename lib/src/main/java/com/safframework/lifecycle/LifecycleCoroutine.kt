package com.safframework.lifecycle

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
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
                                       block: () -> T): Deferred<T> {

    val deferred = GlobalScope.async(context,start) {

        return@async block()
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

infix fun <T> Deferred<T>.then(block: (T) -> Unit): Job {

    return GlobalScope.launch(context = Dispatchers.Main) {

        block(this@then.await())
    }
}

infix fun <T, R> Deferred<T>.then(block: (T) -> R): Deferred<R> {

    return GlobalScope.async(context = Dispatchers.Main) {

        block(this@then.await())
    }
}

suspend fun <T> Deferred<T>.awaitOrNull(timeout: Long = 0L): T? {
    return try {
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
}