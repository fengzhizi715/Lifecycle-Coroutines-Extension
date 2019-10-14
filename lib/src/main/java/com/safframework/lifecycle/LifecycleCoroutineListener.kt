package com.safframework.lifecycle

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.Job

/**
 *
 * @FileName:
 *          com.safframework.lifecycle.LifecycleCoroutineListener
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