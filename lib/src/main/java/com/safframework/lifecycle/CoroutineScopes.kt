package com.safframework.lifecycle

import com.safframework.lifecycle.exception.CoroutineErrorListener
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

fun ioScope(): CoroutineScope = ContextScope(SupervisorJob() + IO)

fun safeIOScope(errorHandler: CoroutineErrorListener?=null): CoroutineScope = ContextScope(SupervisorJob() + IO + UncaughtCoroutineExceptionHandler(errorHandler))

fun uiScope(): CoroutineScope = ContextScope(SupervisorJob() + UI)

fun safeUIScope(errorHandler: CoroutineErrorListener?=null): CoroutineScope = ContextScope(SupervisorJob() + UI + UncaughtCoroutineExceptionHandler(errorHandler))

fun defaultScope(): CoroutineScope = ContextScope(SupervisorJob() + Default)

fun safeDefaultScope(errorHandler: CoroutineErrorListener?=null): CoroutineScope = ContextScope(SupervisorJob() + Default + UncaughtCoroutineExceptionHandler(errorHandler))

fun customScope(dispatcher: CoroutineDispatcher): CoroutineScope = ContextScope(SupervisorJob() + dispatcher)

fun safeCustomScope(dispatcher: CoroutineDispatcher,errorHandler: CoroutineErrorListener?=null): CoroutineScope = ContextScope(SupervisorJob() + dispatcher + UncaughtCoroutineExceptionHandler(errorHandler))

internal class ContextScope(context: CoroutineContext) : CoroutineScope, Closeable {

    override val coroutineContext: CoroutineContext = context

    override fun close() {
        coroutineContext.cancelChildren()
    }
}