package com.xingzhe.ui_library.dataview

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context

/**
 * Created by wumm on 2019/5/14.
 */
class ViewModelCreator {
    companion object {
        fun <T : ViewModel> createViewModel(context: Context, clazz: Class<T>): T {
            return ViewModelProvider.AndroidViewModelFactory.getInstance(context.applicationContext as Application)
                .create(clazz)
        }
    }
}