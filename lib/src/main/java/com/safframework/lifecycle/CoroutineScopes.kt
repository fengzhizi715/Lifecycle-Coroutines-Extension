package com.safframework.lifecycle

import com.safframework.lifecycle.listener.CoroutineErrorListener
import com.safframework.lifecycle.exception.UncaughtCoroutineExceptionHandler
import kotlinx.coroutines.*
import java.io.Closeable
import kotlin.coroutines.CoroutineContext

/**
 *
 * @FileName:
 *          com.safframework.lifecycle.CoroutineScopes
 * @author: Tony Shen
 * @date: 2019-10-16 02:08
 * @version: V1.0 <描述当前版本功能>
 */

fun ioScope(errorHandler: CoroutineErrorListener?=null): CoroutineScope = SafeCoroutineScope(IO,errorHandler)

fun uiScope(errorHandler: CoroutineErrorListener?=null): CoroutineScope = SafeCoroutineScope(UI,errorHandler)

fun defaultScope(errorHandler: CoroutineErrorListener?=null): CoroutineScope = SafeCoroutineScope(Default,errorHandler)

fun customScope(dispatcher: CoroutineDispatcher,errorHandler: CoroutineErrorListener?=null): CoroutineScope = SafeCoroutineScope(dispatcher,errorHandler)

class SafeCoroutineScope(context: CoroutineContext,errorHandler: CoroutineErrorListener?=null) : CoroutineScope, Closeable {

    override val coroutineContext: CoroutineContext = SupervisorJob() + context + UncaughtCoroutineExceptionHandler(errorHandler)

    override fun close() {
        coroutineContext.cancelChildren()
    }
}