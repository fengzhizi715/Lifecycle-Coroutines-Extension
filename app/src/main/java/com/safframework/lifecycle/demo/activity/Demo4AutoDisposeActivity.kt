package com.safframework.lifecycle.demo.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.safframework.lifecycle.IO
import com.safframework.lifecycle.autoDispose
import com.safframework.lifecycle.autoDisposeScope
import com.safframework.lifecycle.demo.R
import com.safframework.lifecycle.demo.utils.ping
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
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
        setContentView(R.layout.activity_layout)

        text1.setOnClickListener {

            val job = GlobalScope.launch {

                ping()
            }

            job.autoDispose(it)
        }

        text2.setOnClickListener {

            text2.autoDisposeScope.launch(IO) {

                println("thread name="+Thread.currentThread().name)
                val result = ping()
                println("result = "+result)
            }
        }
    }

}