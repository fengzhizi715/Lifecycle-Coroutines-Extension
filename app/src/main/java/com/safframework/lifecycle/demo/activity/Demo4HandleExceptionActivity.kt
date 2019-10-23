package com.safframework.lifecycle.demo.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.safframework.lifecycle.UI
import com.safframework.lifecycle.demo.R
import com.safframework.lifecycle.listener.CoroutineErrorListener
import com.safframework.lifecycle.uiScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 *
 * @FileName:
 *          com.safframework.lifecycle.demo.activity.Demo4HandleExceptionActivity
 * @author: Tony Shen
 * @date: 2019-10-20 12:24
 * @version: V1.0 <描述当前版本功能>
 */
class Demo4HandleExceptionActivity: AppCompatActivity() {

    lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_handle_exception)

        mContext = this

        text1.setOnClickListener {

            GlobalScope.launch(UI) {

                Toast.makeText(mContext,"cannot handle the exception", Toast.LENGTH_SHORT).show()

                throw Exception("this is an exception")
            }
        }

        text2.setOnClickListener {

            uiScope().launch {

                Toast.makeText(mContext,"handle the exception", Toast.LENGTH_SHORT).show()

                throw Exception("this is an exception")
            }
        }

        text3.setOnClickListener {

            val errorHandle = object : CoroutineErrorListener {
                override fun onError(throwable: Throwable) {

                    Log.e("errorHandle",throwable.localizedMessage)
                }
            }

            uiScope(errorHandle).launch {

                Toast.makeText(mContext,"handle the exception", Toast.LENGTH_SHORT).show()

                throw Exception("this is an exception")
            }
        }

        text4.setOnClickListener {

            uiScope().launch {

                try {
                    uiScope().async {

                        throw RuntimeException("this is an exception")
                        "doSomething..."
                    }.await()
                } catch (e: Exception) {

                }
            }

            Toast.makeText(mContext,"handle the exception", Toast.LENGTH_SHORT).show()
        }
    }

}