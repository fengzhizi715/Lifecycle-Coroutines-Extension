package com.safframework.lifecycle.demo.activity

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.safframework.kotlin.coroutines.UI
import com.safframework.lifecycle.extension.asyncWithLifecycle
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay

/**
 *
 * @FileName:
 *          com.safframework.lifecycle.demo.activity.Demo4AsyncActivity
 * @author: Tony Shen
 * @date: 2018-12-05 17:01
 * @version: V1.0 <描述当前版本功能>
 */
class Demo4AsyncActivity: AppCompatActivity() {

    lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContext = this

        GlobalScope.asyncWithLifecycle(this, context = UI) {

            delay(1000)

            Toast.makeText(mContext,"hi, this must use 'Dispatchers.Main'",Toast.LENGTH_SHORT).show()
        }
    }

}