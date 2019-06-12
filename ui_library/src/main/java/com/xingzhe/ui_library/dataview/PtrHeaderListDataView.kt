package com.xingzhe.ui_library.dataview

import android.arch.lifecycle.Observer
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import android.view.View
import com.xingzhe.http_library.BaseResponseData
import com.xingzhe.ui_library.recyclerview.BaseRecyclerAdapter

/**
 * Created by wumm on 2019/5/13.
 */
abstract class PtrHeaderListDataView<Data, VM : BaseListViewModel<Data>>(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) :
    PtrListDataView<Data, VM>(context, attrs, defStyle) {

    init {
        getViewModel().headerDataList?.observe(context as AppCompatActivity, Observer {
            onHeaderDataSuccess(it)
        })
    }

    abstract fun getHeaderCount(): Int

    abstract fun createHeaderView(position: Int): View

    abstract fun bindHeaderView(position: Int, data: BaseResponseData<*>)

    override fun createAdapter(): BaseRecyclerAdapter<Data> {
        val adapter = createListAdapter()
        val headerCount = getHeaderCount()
        for (i in 0 until headerCount) {
            adapter.addHeaderView(createHeaderView(i))
        }
        return adapter
    }

    abstract fun createListAdapter(): BaseRecyclerAdapter<Data>

    private fun onHeaderDataSuccess(responses: List<BaseResponseData<*>>?) {
        if (responses == null) {
            return
        }
        for (i in 0 until responses.size) {
            bindHeaderView(i, responses[i])
        }
    }

}