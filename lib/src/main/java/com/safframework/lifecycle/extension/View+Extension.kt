package com.safframework.lifecycle.extension

import android.content.Context
import android.content.ContextWrapper
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.safframework.kotlin.coroutines.SafeCoroutineScope
import com.safframework.kotlin.coroutines.UI
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

// 获取 View 的 lifecycleOwner
val View.lifecycleOwner: LifecycleOwner
    get() = retrieveLifecycleOwner(context)

// 在 Android View 中使用的 Job，能够在 View 的生命周期内自动 Disposable
fun View.autoDispose(job: Job) {

    val listener = ViewListener(this, job)
    this.addOnAttachStateChangeListener(listener)
}

// 在 Android View 中创建 autoDisposeScope，支持主线程运行、异常处理、Job 能够在 View 的生命周期内自动 Disposable
val View.autoDisposeScope: CoroutineScope
    get() {
        return SafeCoroutineScope(UI + ViewAutoDisposeInterceptorImpl(this))
    }

private fun retrieveLifecycleOwner(context: Context): LifecycleOwner {
    return when (context) {
        is LifecycleOwner -> context
        is ContextWrapper -> retrieveLifecycleOwner(context.baseContext)
        else -> ProcessLifecycleOwner.get()
    }
}

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