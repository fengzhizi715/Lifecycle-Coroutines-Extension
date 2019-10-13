package com.safframework.lifecycle

import android.view.View
import kotlinx.coroutines.*
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

/**
 *
 * @FileName:
 *          com.safframework.lifecycle.`View+Extension`
 * @author: Tony Shen
 * @date: 2019-10-10 13:16
 * @version: V1.0 <描述当前版本功能>
 */
fun View.autoDispose(job: Job) {

    val listener = ViewListener(this, job)
    this.addOnAttachStateChangeListener(listener)
}

fun View.autoDisposeInterceptor(): ContinuationInterceptor = ViewAutoDisposeInterceptorImpl(this)

val View.autoDisposeScope: CoroutineScope
    get() {
        return ViewCoroutineScope(SupervisorJob() + UI + autoDisposeInterceptor())
    }

private class ViewCoroutineScope(
        override val coroutineContext: CoroutineContext
) : CoroutineScope

private class ViewListener(
        private val view: View,
        private val job: Job
) : View.OnAttachStateChangeListener,
        CompletionHandler {
    override fun onViewDetachedFromWindow(v: View) {
        view.removeOnAttachStateChangeListener(this)
        job.cancel()
    }

    override fun onViewAttachedToWindow(v: View) {
    }

    override fun invoke(cause: Throwable?) {
        view.removeOnAttachStateChangeListener(this)
        job.cancel()
    }
}

private class ViewAutoDisposeInterceptorImpl(
        private val view: View
) : ContinuationInterceptor {
    override val key: CoroutineContext.Key<*>
        get() = ContinuationInterceptor

    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {

        continuation.context[Job]?.let {
            view.autoDispose(it)
        }

        return continuation
    }
}