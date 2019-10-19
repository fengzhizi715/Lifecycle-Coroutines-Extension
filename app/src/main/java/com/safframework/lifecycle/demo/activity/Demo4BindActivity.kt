package com.safframework.lifecycle.demo.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.safframework.lifecycle.extension.bindWithLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

/**
 *
 * @FileName:
 *          com.safframework.lifecycle.demo.activity.Demo4BindActivity
 * @author: Tony Shen
 * @date: 2018-12-05 17:17
 * @version: V1.0 <描述当前版本功能>
 */
class Demo4BindActivity: AppCompatActivity() {

    lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContext = this

        GlobalScope.bindWithLifecycle(this) {

            GlobalScope.async(Dispatchers.Main) {

                val deferred1 = async(Dispatchers.Default) {

                    delay(1000)
                    1
                }

                val deferred2 = async(Dispatchers.Default) {

                    delay(1500)
                    2
                }

                val result = deferred1.await() + deferred2.await()

                Toast.makeText(mContext,"the result is $result",Toast.LENGTH_SHORT).show()
            }
        }
    }
}