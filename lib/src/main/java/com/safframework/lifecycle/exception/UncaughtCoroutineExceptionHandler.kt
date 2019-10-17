package com.safframework.lifecycle.exception

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

/**
 *
 * @FileName:
 *          com.safframework.lifecycle.exception.UncaughtCoroutineExceptionHandler
 * @author: Tony Shen
 * @date: 2019-10-18 02:13
 * @version: V1.0 <描述当前版本功能>
 */
class UncaughtCoroutineExceptionHandler(val errorHandler: CoroutineErrorListener?=null)  :
        CoroutineExceptionHandler, AbstractCoroutineContextElement(CoroutineExceptionHandler.Key) {

    override fun handleException(context: CoroutineContext, throwable: Throwable) {
        Log.e("Coroutines Error", throwable.localizedMessage)

        errorHandler?.let {
            it.onError(throwable)
        }
    }
}