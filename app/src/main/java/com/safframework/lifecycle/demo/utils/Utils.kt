package com.safframework.lifecycle.demo.utils

import android.util.Log
import java.lang.Exception

/**
 *
 * @FileName:
 *          com.safframework.lifecycle.demo.utils.Utils
 * @author: Tony Shen
 * @date: 2019-10-14 16:27
 * @version: V1.0 <描述当前版本功能>
 */
fun ping(): Boolean {
    try {
        val ip = "www.baidu.com"// ping 的地址，可以换成任何一种可靠的外网
        val p = Runtime.getRuntime().exec("ping -c 1 -w 100 $ip")// ping网址3次
        // ping的状态
        val status = p.waitFor()
        if (status == 0) {
            return true
        }
    } catch (e: Exception) {
        Log.d("ping","ping失败："+e.message)
    } finally {
    }
    return false
}