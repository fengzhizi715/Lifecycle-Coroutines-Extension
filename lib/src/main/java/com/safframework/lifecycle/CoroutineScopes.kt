package com.safframework.lifecycle

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

fun uiScope(): CoroutineScope = ContextScope(SupervisorJob() + UI)

fun defaultScope(): CoroutineScope = ContextScope(SupervisorJob() + Dispatchers.Default)

fun customScope(dispatcher: CoroutineDispatcher): CoroutineScope = ContextScope(SupervisorJob() + dispatcher)

internal class ContextScope(context: CoroutineContext) : CoroutineScope, Closeable {

    override val coroutineContext: CoroutineContext = context

    override fun close() {
        coroutineContext.cancelChildren()
    }
}