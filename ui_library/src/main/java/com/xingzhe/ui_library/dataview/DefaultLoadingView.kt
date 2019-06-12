package com.xingzhe.ui_library.dataview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.xingzhe.ui_library.R

/**
 * Created by wumm on 2019/5/9.
 */
class DefaultLoadingView(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LoadingView(context, attrs, defStyleAttr) {
    override fun createEmptyView(): View? {
        return View.inflate(context, R.layout.view_defalult_loadiing_empty, null)
    }

    override fun createLoadingView(): View? {
        return View.inflate(context, R.layout.view_defalult_loadiing, null)
    }

    override fun createErrorView(error: BaseObserver.ResponseError?): View? {
        val errorView = View.inflate(context, R.layout.view_defalult_loadiing_error, null)
        val reasonTv = errorView.findViewById<TextView>(R.id.reason_tv)
        reasonTv.text = error?.errorMessage
        return errorView
    }

}