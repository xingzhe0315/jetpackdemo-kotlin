package com.xingzhe.ui_library.ptr

import `in`.srain.cube.views.ptr.PtrFrameLayout
import `in`.srain.cube.views.ptr.PtrUIHandler
import `in`.srain.cube.views.ptr.indicator.PtrIndicator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout

/**
 * Created by wumm on 2019/5/10.
 */
abstract class PtrBaseHeaderView(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    RelativeLayout(context, attrs, defStyleAttr), PtrUIHandler {
    var isRefreshing:Boolean = false
    init {
        this.addView(this.createHeaderView(context))
    }

    override fun onUIRefreshComplete(frame: PtrFrameLayout?) {
        isRefreshing  = false
        setSuccessState()
    }

    override fun onUIPositionChange(
        frame: PtrFrameLayout?,
        isUnderTouch: Boolean,
        status: Byte,
        ptrIndicator: PtrIndicator?
    ) {
        onPositionMove(
            frame,
            isUnderTouch,
            status,
            ptrIndicator
        )
    }

    override fun onUIRefreshBegin(frame: PtrFrameLayout?) {
        setRefreshingState()
        isRefreshing = true
    }

    override fun onUIRefreshPrepare(frame: PtrFrameLayout?) {

    }

    override fun onUIReset(frame: PtrFrameLayout?) {
        isRefreshing = false
        setNormalState()
    }

    abstract fun setNormalState()

    abstract fun setReadyState()

    abstract fun setSuccessState()

    abstract fun setRefreshingState()

    abstract fun onPositionMove(
        frame: PtrFrameLayout?,
        isUnderTouch: Boolean,
        status: Byte,
        ptrIndicator: PtrIndicator?
    )

    abstract fun createHeaderView(context: Context): View

}