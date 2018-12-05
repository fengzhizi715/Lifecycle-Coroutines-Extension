package com.safframework.lifecycle.demo.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.safframework.lifecycle.demo.R
import kotlinx.android.synthetic.main.activity_main.*

/**
 *
 * @FileName:
 *          com.safframework.lifecycle.demo.activity.MainActivity
 * @author: Tony Shen
 * @date: 2018-12-05 15:54
 * @version: V1.0 <描述当前版本功能>
 */
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text1.setOnClickListener {

            val i = Intent(this,Demo4AsyncActivity::class.java)
            startActivity(i)
        }

        text2.setOnClickListener {

            val i = Intent(this,Demo4BindActivity::class.java)
            startActivity(i)
        }
    }
}