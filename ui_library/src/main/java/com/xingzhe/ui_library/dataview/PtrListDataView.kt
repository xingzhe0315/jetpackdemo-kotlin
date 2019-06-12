package com.xingzhe.ui_library.dataview

import android.arch.lifecycle.Observer
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import com.xingzhe.ui_library.ptr.DefaultLoadMoreView
import com.xingzhe.ui_library.ptr.LoadMoreView
import com.xingzhe.ui_library.recyclerview.BaseRecyclerAdapter

/**
 * Created by wumm on 2019/5/13.
 */
abstract class PtrListDataView<Data, VM : BaseListViewModel<Data>>(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : PtrDataView<List<Data>, VM>(context, attrs, defStyle) {
    lateinit var adapter: BaseRecyclerAdapter<Data>
    lateinit var loadMoreFooter:LoadMoreView

    var hasMore:Boolean = true
    var canLoadMore:Boolean = true

    init {
        getViewModel().appendDataList.observe(context as AppCompatActivity, Observer {
            if (it != null)
                appendDataToList(it)
        })

        getViewModel().loadMoreError.observe(context, Observer {
            if (it != null)
                onLoadMoreError(it)
        })
    }

    override fun refresh() {
        getViewModel().refresh()
    }

    override fun createView(context: Context): View {
        val recyclerView = RecyclerView(context)
        adapter = createAdapter()
        loadMoreFooter = createLoadMoreView(context)
        adapter.addFooterView(loadMoreFooter as View)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        recyclerView.addOnChildAttachStateChangeListener(object :RecyclerView.OnChildAttachStateChangeListener{
            override fun onChildViewDetachedFromWindow(view: View) {
            }

            override fun onChildViewAttachedToWindow(view: View) {
                if (view is LoadMoreView){
                    if (hasMore && canLoadMore && !isRefreshing){
                        getViewModel().loadMore()
                    }
                }
            }

        })
        return recyclerView
    }

    private fun createLoadMoreView(context: Context): LoadMoreView {
        val loadMoreView = DefaultLoadMoreView(context)
        loadMoreView.retryHandler = object : DataRetryHandler {
            override fun onDataRetry() {
                loadMore()
            }
        }
        loadMoreView.layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.WRAP_CONTENT)
        return loadMoreView
    }

    abstract fun createAdapter(): BaseRecyclerAdapter<Data>

    override fun bindView(data: List<Data>) {
        adapter.dataSetAndNotify(data as ArrayList<Data>)
        hasMore = true
    }

    private fun loadMore(){
        loadMoreFooter.onLoading()
        getViewModel().loadMore()
    }

    fun appendDataToList(appendList: List<Data>) {
        if (appendList.isEmpty()){
            loadMoreFooter.onEnd()
            hasMore = false
            return
        }
        adapter.dataAppendAndNotify(appendList as ArrayList<Data>)
    }

    fun onLoadMoreError(error: BaseObserver.ResponseError) {
        loadMoreFooter.onLoadMoreError(error)
    }
}