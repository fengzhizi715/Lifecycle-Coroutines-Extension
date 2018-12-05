package com.safframework.lifecycle

import android.arch.lifecycle.GenericLifecycleObserver
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import kotlinx.coroutines.Deferred

/**
 *
 * @FileName:
 *          com.safframework.lifecycle.LifecycleCoroutine
 * @author: Tony Shen
 * @date: 2018-12-05 14:02
 * @version: V1.0 <描述当前版本功能>
 */
class CoroutineLifecycleListener(
        private val deferred: Deferred<*>,
        private val untilEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY
) : GenericLifecycleObserver {
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        //if the event is what you need
        if (event == untilEvent) {
            //if the job is not cancelled
            if (!deferred.isCancelled) {
                deferred.cancel()
            }
            //remove the observer
            source.lifecycle.removeObserver(this)
        }
    }
}

