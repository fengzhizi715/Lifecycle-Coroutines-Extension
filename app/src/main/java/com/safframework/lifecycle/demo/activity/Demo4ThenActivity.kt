package com.safframework.lifecycle.demo.activity

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.safframework.kotlin.coroutines.IO
import com.safframework.lifecycle.extension.asyncWithLifecycle
import com.safframework.lifecycle.extension.then
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay

/**
 *
 * @FileName:
 *          com.safframework.lifecycle.demo.activity.Demo4ThenActivity
 * @author: Tony Shen
 * @date: 2018-12-05 17:31
 * @version: V1.0 <描述当前版本功能>
 */
class Demo4ThenActivity: AppCompatActivity() {

    lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContext = this

        GlobalScope.asyncWithLifecycle(this,IO) {

            delay(5000) // 模拟耗时的网络请求
            1
        } then {

            Toast.makeText(mContext,"the result is $it",Toast.LENGTH_SHORT).show()
        }
    }
}