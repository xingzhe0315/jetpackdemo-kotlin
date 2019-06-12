package com.xingzhe.ui_library.dataview

import `in`.srain.cube.views.ptr.PtrDefaultHandler
import `in`.srain.cube.views.ptr.PtrFrameLayout
import `in`.srain.cube.views.ptr.PtrHandler
import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.xingzhe.ui_library.ptr.PtrContainer

/**
 * Created by wumm on 2019/5/10.
 */
abstract class PtrDataView<Data, VM : BaseViewModel<Data>>(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : PtrContainer(context, attrs, defStyle) {
    private var innerDataView: InnerDataView
    var canRefresh:Boolean = true
    init {
        this.setPtrHandler(object : PtrHandler {
            override fun onRefreshBegin(frame: PtrFrameLayout?) {
                refresh()
            }

            override fun checkCanDoRefresh(frame: PtrFrameLayout?, content: View?, header: View?): Boolean {
                return canRefresh && PtrDefaultHandler.checkContentCanBePulledDown(frame, getScrollableContentView(content), header)
            }
        })

        innerDataView = InnerDataView(context)
        this.addView(innerDataView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        this.onFinishInflate()
    }

    fun startLoad() {
        innerDataView.startLoad()
    }

    open fun refresh() {
        innerDataView.startLoad()
    }

    fun getViewModel(): VM {
        return innerDataView.viewModel
    }

    private fun getScrollableContentView(content: View?): View? {
        return if (innerDataView.contentView == null) content else innerDataView.contentView
    }

    abstract fun createViewModel(): VM

    abstract fun createView(context: Context): View

    abstract fun bindView(data: Data)

    inner class InnerDataView(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        SimpleDataView<Data, VM>(context, attrs, defStyleAttr) {
        override fun createViewModel(): VM {
            return this@PtrDataView.createViewModel()
        }

        override fun createView(context: Context): View {
            return this@PtrDataView.createView(context)
        }

        override fun bindView(data: Data) {
            this@PtrDataView.bindView(data)
        }

        override fun onDataSuccess(data: Data?) {
            super.onDataSuccess(data)
            this@PtrDataView.refreshComplete()
        }

        override fun onDataError(error: BaseObserver.ResponseError?) {
            super.onDataError(error)
            this@PtrDataView.refreshComplete()
        }
    }
}