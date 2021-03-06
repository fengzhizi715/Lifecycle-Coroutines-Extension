package com.safframework.lifecycle.demo.activity

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.safframework.kotlin.coroutines.asyncInBackground
import com.safframework.kotlin.coroutines.runOnUI
import kotlinx.coroutines.delay

/**
 *
 * @FileName:
 *          com.safframework.lifecycle.demo.activity.Demo4RunOnUIActivity
 * @author: Tony Shen
 * @date: 2019-08-24 21:48
 * @version: V1.0 <描述当前版本功能>
 */
class Demo4RunOnUIActivity: AppCompatActivity() {

    lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContext = this

        runOnUI {

            Toast.makeText(mContext,"hi, this must use 'Dispatchers.Main'", Toast.LENGTH_SHORT).show()
        }

        runOnUI {

            val deferred= asyncInBackground {

                "hi background"
            }

            delay(2000)

            Toast.makeText(mContext,"the result is '${deferred.await()}'", Toast.LENGTH_SHORT).show()
        }
    }

}