package com.xingzhe.ui_library.ptr

import `in`.srain.cube.views.ptr.PtrFrameLayout
import android.content.Context
import android.util.AttributeSet

/**
 * Created by wumm on 2019/5/10.
 */
open class PtrContainer(context: Context?, attrs: AttributeSet?, defStyle: Int) : PtrFrameLayout(context, attrs, defStyle) {
    init {
        val ptrHeader = this.createHeaderView()
        this.setHeaderView(ptrHeader)
        this.addPtrUIHandler(ptrHeader)
    }

    protected fun createHeaderView():PtrBaseHeaderView{
        return PtrDefaultHeaderView(context)
    }

}