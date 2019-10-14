package com.safframework.lifecycle.demo.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.safframework.lifecycle.demo.R

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
    }

}