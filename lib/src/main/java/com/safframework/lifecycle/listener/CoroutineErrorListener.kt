package com.safframework.lifecycle.listener

/**
 *
 * @FileName:
 *          com.safframework.lifecycle.listener.CoroutineErrorListener
 * @author: Tony Shen
 * @date: 2019-10-18 02:12
 * @version: V1.0 <描述当前版本功能>
 */
interface CoroutineErrorListener {

    fun onError(throwable: Throwable)
}