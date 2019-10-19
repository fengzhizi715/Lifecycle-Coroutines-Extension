package com.safframework.lifecycle

import com.safframework.lifecycle.listener.CoroutineErrorListener
import kotlinx.coroutines.*

/**
 *
 * @FileName:
 *          com.safframework.lifecycle.CoroutineScopes
 * @author: Tony Shen
 * @date: 2019-10-16 02:08
 * @version: V1.0 <描述当前版本功能>
 */
val UI: CoroutineDispatcher      = Dispatchers.Main

val IO: CoroutineDispatcher      = Dispatchers.IO

val Default: CoroutineDispatcher = Dispatchers.Default


fun runOnUI(block: suspend CoroutineScope.() -> Unit) = uiScope().launch(block = block)

fun runInBackground(block: suspend CoroutineScope.() -> Unit) = ioScope().launch(block = block)

fun <T> asyncOnUI(block: suspend CoroutineScope.() -> T) = uiScope().async(block = block)

fun <T> asyncInBackground(block: suspend CoroutineScope.() -> T) = ioScope().async(block = block)


fun ioScope(errorHandler: CoroutineErrorListener?=null) = SafeCoroutineScope(IO,errorHandler)

fun uiScope(errorHandler: CoroutineErrorListener?=null) = SafeCoroutineScope(UI,errorHandler)

fun defaultScope(errorHandler: CoroutineErrorListener?=null) = SafeCoroutineScope(Default,errorHandler)

fun customScope(dispatcher: CoroutineDispatcher,errorHandler: CoroutineErrorListener?=null) = SafeCoroutineScope(dispatcher,errorHandler)