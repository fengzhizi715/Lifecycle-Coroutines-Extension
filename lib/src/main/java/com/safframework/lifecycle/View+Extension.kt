package com.safframework.lifecycle

import android.view.View
import kotlinx.coroutines.CompletionHandler
import kotlinx.coroutines.Job

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