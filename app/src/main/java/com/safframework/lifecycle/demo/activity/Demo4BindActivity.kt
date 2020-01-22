package com.safframework.lifecycle.demo.activity

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.safframework.lifecycle.*
import com.safframework.lifecycle.extension.bindWithLifecycle
import kotlinx.coroutines.*

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

            asyncOnUI {

                val deferred1 = asyncInBackground {
                    delay(1000)
                    1
                }

                val deferred2 = asyncInBackground {
                    delay(1500)
                    2
                }

                val result = deferred1.await() + deferred2.await()

                Toast.makeText(mContext,"the result is $result",Toast.LENGTH_SHORT).show()
            }
        }
    }
}