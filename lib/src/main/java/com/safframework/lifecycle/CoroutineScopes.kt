package com.safframework.lifecycle

import com.safframework.lifecycle.listener.CoroutineErrorListener
import kotlinx.coroutines.CoroutineDispatcher

/**
 *
 * @FileName:
 *          com.safframework.lifecycle.CoroutineScopes
 * @author: Tony Shen
 * @date: 2019-10-16 02:08
 * @version: V1.0 <描述当前版本功能>
 */

fun ioScope(errorHandler: CoroutineErrorListener?=null) = SafeCoroutineScope(IO,errorHandler)

fun uiScope(errorHandler: CoroutineErrorListener?=null) = SafeCoroutineScope(UI,errorHandler)

fun defaultScope(errorHandler: CoroutineErrorListener?=null) = SafeCoroutineScope(Default,errorHandler)

fun customScope(dispatcher: CoroutineDispatcher,errorHandler: CoroutineErrorListener?=null) = SafeCoroutineScope(dispatcher,errorHandler)