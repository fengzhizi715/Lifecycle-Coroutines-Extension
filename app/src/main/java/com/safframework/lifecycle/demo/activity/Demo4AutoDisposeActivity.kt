package com.safframework.lifecycle.demo.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.safframework.kotlin.coroutines.defaultScope
import com.safframework.lifecycle.demo.R
import com.safframework.lifecycle.demo.utils.ping
import com.safframework.lifecycle.extension.autoDispose
import com.safframework.lifecycle.extension.autoDisposeScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch

/**
 *
 * @FileName:
 *          com.safframework.lifecycle.demo.activity.Demo4AutoDisposeActivity
 * @author: Tony Shen
 * @date: 2019-10-14 16:13
 * @version: V1.0 <描述当前版本功能>
 */
class Demo4AutoDisposeActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auto_dispose)

        text1.setOnClickListener {

            val job = defaultScope().launch {

                val result = ping()
                println("result = $result")
            }

            job.autoDispose(it)
        }

        text2.setOnClickListener {

            text2.autoDisposeScope.launch {

                val result = ping()
                println("result = $result")
            }
        }
    }

}