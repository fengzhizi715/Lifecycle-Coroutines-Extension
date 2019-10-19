package com.safframework.lifecycle

import com.safframework.lifecycle.exception.UncaughtCoroutineExceptionHandler
import com.safframework.lifecycle.listener.CoroutineErrorListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import java.io.Closeable
import kotlin.coroutines.CoroutineContext

/**
 *
 * @FileName:
 *          com.safframework.lifecycle.SafeCoroutineScope
 * @author: Tony Shen
 * @date: 2019-10-19 12:43
 * @version: V1.0 <描述当前版本功能>
 */
class SafeCoroutineScope(context: CoroutineContext, errorHandler: CoroutineErrorListener?=null) : CoroutineScope, Closeable {

    override val coroutineContext: CoroutineContext = SupervisorJob() + context + UncaughtCoroutineExceptionHandler(errorHandler)

    override fun close() {
        coroutineContext.cancelChildren()
    }
}