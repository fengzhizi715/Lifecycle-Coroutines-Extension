package com.safframework.lifecycle

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlin.reflect.jvm.internal.impl.load.java.JavaClassesTracker

/**
 *
 * @FileName:
 *          com.safframework.lifecycle.Constant
 * @author: Tony Shen
 * @date: 2019-10-16 01:28
 * @version: V1.0 <描述当前版本功能>
 */
val UI: CoroutineDispatcher = Dispatchers.Main

val IO: CoroutineDispatcher = Dispatchers.IO

val Default: CoroutineDispatcher = Dispatchers.Default