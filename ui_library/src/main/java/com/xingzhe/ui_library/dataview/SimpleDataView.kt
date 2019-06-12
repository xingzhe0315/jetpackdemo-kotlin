package com.xingzhe.ui_library.dataview

import android.arch.lifecycle.Observer
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout

/**
 * Created by wumm on 2019/5/9.
 */
abstract class SimpleDataView<Data, VM : BaseViewModel<Data>> constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    var viewModel: VM
    private var loadingView: LoadingView
    var contentView: View? = null

    init {
        viewModel = this.createViewModel()
        loadingView = createLoadingView()
        loadingView.retryHandler = object : DataRetryHandler {
            override fun onDataRetry() {
                startLoad()
            }
        }
        viewModel.liveData.observe(context as AppCompatActivity,
            Observer<Data> { t -> onDataSuccess(t) })
        viewModel.errorLiveData.observe(context,
            Observer<BaseObserver.ResponseError> { error -> onDataError(error) })
        this.addView(loadingView)
    }

    protected abstract fun createViewModel(): VM

    protected abstract fun createView(context: Context): View

    protected abstract fun bindView(data: Data)

    fun startLoad() {
        viewModel.startLoad()
    }

    open fun onDataSuccess(data: Data?) {
        if (contentView == null) {
            contentView = createView(context)
            addView(contentView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        }
        bindView(data!!)
        loadingView.setState(LoadingView.LoadingState.STATE_SUCCESS)
    }


    open fun onDataError(error: BaseObserver.ResponseError?) {
        loadingView.error = error
        loadingView.setState(LoadingView.LoadingState.STATE_ERROR)
    }

    private fun createLoadingView(): LoadingView {
        return DefaultLoadingView(context)
    }
}