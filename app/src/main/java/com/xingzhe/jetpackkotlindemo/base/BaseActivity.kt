package com.xingzhe.jetpackkotlindemo.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.SparseArray

/**
 * Created by wumm on 2019/5/20.
 */
abstract class BaseActivity : AppCompatActivity() {

    abstract fun initDataFromIntent(intent: Intent)

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        initDataFromIntent(intent)
        notifyActivityCreate()
    }

    override fun onStart() {
        super.onStart()
        notifyActivityStart()
    }

    override fun onResume() {
        super.onResume()
        notifyActivityResume()
    }

    override fun onPause() {
        super.onPause()
        notifyActivityPause()
    }

    override fun onStop() {
        super.onStop()
        notifyActivityStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        notifyActivityDestroy()

        onActivityResultListeners?.clear()
        onActivityResultListeners = null
        onActivityLifeCycleListeners?.clear()
        onActivityLifeCycleListeners = null
    }

    override fun onRestart() {
        super.onRestart()
        notifyActivityRestart()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val listener = onActivityResultListeners?.get(requestCode)
        if (listener != null) {
            listener.onActivityResult(this, requestCode, resultCode, data)
            removeActivityResultListener(requestCode)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun startActivityForResult(intent: Intent?, requestCode: Int, listener: OnActivityResultListener) {
        super.startActivityForResult(intent, requestCode)
        addActivityResultListener(requestCode, listener)
    }


    private var onActivityResultListeners: SparseArray<OnActivityResultListener>? = null


    fun addActivityResultListener(reqCode: Int, listener: OnActivityResultListener) {
        if (onActivityResultListeners == null)
            onActivityResultListeners = SparseArray()
        onActivityResultListeners?.put(reqCode, listener)
    }

    fun removeActivityResultListener(reqCode: Int) {
        onActivityResultListeners?.remove(reqCode)
    }

    interface OnActivityResultListener {
        fun onActivityResult(context: Context, requestCode: Int, resultCode: Int, data: Intent?)
    }

    private fun notifyActivityCreate() {
        onActivityLifeCycleListeners?.forEach {
            it.onActivityCreate()
        }
    }

    private fun notifyActivityStart() {
        onActivityLifeCycleListeners?.forEach {
            it.onActivityStart()
        }
    }

    private fun notifyActivityResume() {
        onActivityLifeCycleListeners?.forEach {
            it.onActivityResume()
        }
    }

    private fun notifyActivityPause() {
        onActivityLifeCycleListeners?.forEach {
            it.onActivityPause()
        }
    }

    private fun notifyActivityStop() {
        onActivityLifeCycleListeners?.forEach {
            it.onActivityStop()
        }
    }

    private fun notifyActivityDestroy() {
        onActivityLifeCycleListeners?.forEach {
            it.onActivityDestroy()
        }
    }

    private fun notifyActivityRestart() {
        onActivityLifeCycleListeners?.forEach {
            it.onActivityRestart()
        }
    }

    private var onActivityLifeCycleListeners: ArrayList<OnActivityLifeCycleListener>? = null

    fun addActivityLifeCycleListener(listener: OnActivityLifeCycleListener) {
        if (onActivityLifeCycleListeners == null) {
            onActivityLifeCycleListeners = ArrayList()
        }
        onActivityLifeCycleListeners?.add(listener)
    }

    interface OnActivityLifeCycleListener {
        fun onActivityCreate()

        fun onActivityStart()

        fun onActivityResume()

        fun onActivityPause()

        fun onActivityStop()

        fun onActivityDestroy()

        fun onActivityRestart()
    }

    class SimpleActivityLifeCycleListener : OnActivityLifeCycleListener {
        override fun onActivityCreate() {

        }

        override fun onActivityStart() {
        }

        override fun onActivityResume() {
        }

        override fun onActivityPause() {
        }

        override fun onActivityStop() {
        }

        override fun onActivityDestroy() {
        }

        override fun onActivityRestart() {
        }

    }

}