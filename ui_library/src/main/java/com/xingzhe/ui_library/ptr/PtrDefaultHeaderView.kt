package com.xingzhe.ui_library.ptr

import `in`.srain.cube.views.ptr.PtrFrameLayout
import `in`.srain.cube.views.ptr.PtrUIHandler
import `in`.srain.cube.views.ptr.indicator.PtrIndicator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.xingzhe.ui_library.R
import com.xingzhe.util_library.DensityUtil

/**
 * Created by wumm on 2019/5/10.
 */
class PtrDefaultHeaderView(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    PtrBaseHeaderView(context, attrs, defStyleAttr), PtrUIHandler {

    private var tipsTv: TextView? = null
    private var progressBar: ProgressBar? = null

    private val statePullToRefresh:String = "下拉刷新"
    private val stateReadyToRefresh:String = "释放刷新"
    private val stateSuccess:String = "刷新成功"
    private val stateRefreshing:String = "正在刷新..."

    override fun setNormalState(){
        tipsTv?.text = statePullToRefresh
    }

    override fun setReadyState(){
        tipsTv?.text = stateReadyToRefresh
    }

    override fun  setSuccessState(){
        tipsTv?.text = stateSuccess
    }

    override fun  setRefreshingState(){
        tipsTv?.text = stateRefreshing
    }

    override fun onPositionMove(
        frame: PtrFrameLayout?,
        isUnderTouch: Boolean,
        status: Byte,
        ptrIndicator: PtrIndicator?
    ) {
        if (isRefreshing){
            return
        }
        if (ptrIndicator!=null && ptrIndicator.currentPercent >= 1f){
            setReadyState()
        } else {
            setNormalState()
        }
    }

    override fun createHeaderView(context: Context): View {
        val view = View.inflate(context, R.layout.view_default_header, null)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(context,60f))
        tipsTv = view.findViewById(R.id.refresh_tips)
        progressBar = view.findViewById(R.id.progress)
        return view
    }

}