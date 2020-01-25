package com.safframework.lifecycle.demo.activity

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.safframework.kotlin.coroutines.IO
import com.safframework.kotlin.coroutines.UI
import com.safframework.kotlin.coroutines.extension.awaitOrNull
import com.safframework.lifecycle.extension.asyncWithLifecycle
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay

/**
 *
 * @FileName:
 *          com.safframework.lifecycle.demo.activity.Demo4AwaitOrNullActivity
 * @author: Tony Shen
 * @date: 2018-12-05 19:00
 * @version: V1.0 <描述当前版本功能>
 */
class Demo4AwaitOrNullActivity: AppCompatActivity() {

    lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContext = this

        val deferred = GlobalScope.asyncWithLifecycle(this, context = IO) {

            delay(5000) // 模拟耗时的网络请求
            1
        }

        GlobalScope.asyncWithLifecycle(this, context = UI) {

            val result = deferred.awaitOrNull(4000)

            Toast.makeText(mContext,"the result is $result", Toast.LENGTH_SHORT).show()
        }
    }
}