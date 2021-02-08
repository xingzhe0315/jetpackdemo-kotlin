package com.xingzhe.ui_library.dataview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout

/**
 * Created by wumm on 2019/5/9.
 */
abstract class LoadingView(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : RelativeLayout(context, attrs, defStyleAttr) {
    var retryHandler: DataRetryHandler? = null
    private var mLoadingView: View? = null
    private var mErrorView: View? = null
    private var mEmptyView: View? = null
    var error: BaseObserver.ResponseError? = null

    fun setState(state: Int) {
        when (state) {
            LoadingState.STATE_LOADING -> showLoading()
            LoadingState.STATE_EMPTY -> showEmpty()
            LoadingState.STATE_ERROR -> showError()
            LoadingState.STATE_SUCCESS -> visibility = GONE
        }
    }

    private fun showView(view: View?) {
        view ?: return

        removeAllViews()
        if (view.parent == null) {
            if (view.layoutParams == null) {
                view.layoutParams = createLayoutParams()
            }
            addView(view)
        }
    }

    private fun showLoading() {
        if (mLoadingView == null) {
            mLoadingView = createLoadingView()
            if (mLoadingView?.layoutParams == null) {
                mLoadingView?.layoutParams = createLayoutParams()
            }
        }
        showView(mLoadingView)
    }

    private fun showError() {
        if (mErrorView == null) {
            mErrorView = createErrorView(error)
            if (mErrorView?.layoutParams == null) {
                mErrorView?.layoutParams = createLayoutParams()
            }
            mErrorView?.setOnClickListener { retryHandler?.onDataRetry() }
        }
    }

    private fun showEmpty() {
        if (mEmptyView == null) {
            mEmptyView = createEmptyView()
            if (mEmptyView?.layoutParams == null) {
                mEmptyView?.layoutParams = createLayoutParams()
            }
        }
        showView(mEmptyView)
    }

    abstract fun createEmptyView(): View?

    abstract fun createLoadingView(): View?

    abstract fun createErrorView(error: BaseObserver.ResponseError?): View?

    private fun createLayoutParams(): LayoutParams {
        return LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    }


    class LoadingState {
        companion object {
            const val STATE_ERROR: Int = -1
            const val STATE_LOADING: Int = 0
            const val STATE_EMPTY: Int = 1
            const val STATE_SUCCESS = 2
        }

    }

}