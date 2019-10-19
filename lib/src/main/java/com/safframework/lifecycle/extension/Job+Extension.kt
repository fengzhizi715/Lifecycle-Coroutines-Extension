package com.safframework.lifecycle.extension

import android.os.Build
import android.view.View
import kotlinx.coroutines.Job

/**
 *
 * @FileName:
 *          com.safframework.lifecycle.`Job+Extension`
 * @author: Tony Shen
 * @date: 2019-10-13 14:21
 * @version: V1.0 <描述当前版本功能>
 */
fun Job.autoDispose(view: View) = AutoDisposableJob(view, this)

class AutoDisposableJob(private val view: View, private val job: Job)
    : Job by job, View.OnAttachStateChangeListener {

    private fun isViewAttached() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && view.isAttachedToWindow || view.windowToken != null

    init {
        if(isViewAttached()) {
            view.addOnAttachStateChangeListener(this)
        } else {
            cancel()
        }

        invokeOnCompletion {
            view.post {
                view.removeOnAttachStateChangeListener(this)
            }
        }
    }

    override fun onViewAttachedToWindow(v: View?) = Unit

    override fun onViewDetachedFromWindow(v: View?) {
        cancel()
        view.removeOnAttachStateChangeListener(this)
    }
}