package com.xingzhe.ui_library.recyclerview

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

/**
 * Created by wumm on 2019/5/10.
 */
abstract class BaseRecyclerAdapter<Data> : RecyclerView.Adapter<CommonRecyclerViewHolder>() {
    private var dataList: ArrayList<Data>? = null

    private val headerList: MutableList<View> = ArrayList(5)
    private val footerList: MutableList<View> = ArrayList(5)

    companion object {
        private const val HEADER_TYPE: Int = 100
        private const val FOOTER_TYPE: Int = 200
    }

    fun dataSetAndNotify(appendData: ArrayList<Data>?) {
        dataSet(appendData)
        notifyDataSetChanged()
    }

    private fun dataSet(dataSet: ArrayList<Data>?) {
        dataList = dataSet
    }

    fun dataAppendAndNotify(appendData: ArrayList<Data>?) {
        appendData ?: return

        val startIndex = itemCount
        dataAppend(appendData)
        notifyItemRangeInserted(startIndex, appendData.size)
    }

    private fun dataAppend(appendData: ArrayList<Data>) {
        if (dataList == null)
            dataList = ArrayList()
        dataList?.addAll(appendData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonRecyclerViewHolder {
        if (viewType >= FOOTER_TYPE) {
            return CommonRecyclerViewHolder(getFooterViewAt(viewType - FOOTER_TYPE))
        } else if (viewType >= HEADER_TYPE) {
            return CommonRecyclerViewHolder(getHeaderViewAt(viewType - HEADER_TYPE))
        }
        return CommonRecyclerViewHolder(createItemView(parent.context))
    }

    override fun getItemCount(): Int {
        return getHeaderCount() + getDataSize() + getFooterCount()
    }

    override fun onBindViewHolder(viewHolder: CommonRecyclerViewHolder, position: Int) {
        if (position < getHeaderCount()) {
            bindHeaderView(viewHolder, position);
        } else if (position >= getHeaderCount() + getDataSize()) {
            bindFooterView(viewHolder, position - getHeaderCount() - getDataSize())
        } else {
            val realPosition = position - getHeaderCount()
            bindItemView(viewHolder, realPosition, dataList?.get(realPosition))
            viewHolder.itemView.setOnClickListener {
                val item = dataList?.getOrNull(realPosition) ?: return@setOnClickListener

                itemClickListener?.onItemClick(realPosition, item)
            }
        }
    }

    private fun bindHeaderView(viewHolder: CommonRecyclerViewHolder, position: Int) {
    }

    private fun bindFooterView(viewHolder: CommonRecyclerViewHolder, position: Int) {
    }

    private fun getDataSize(): Int = dataList?.run { size } ?: 0

    fun addHeaderView(header: View) {
        headerList.add(header)
        notifyDataSetChanged()
    }

    fun addFooterView(footer: View) {
        footerList.add(footer)
        notifyDataSetChanged()
    }

    fun removeHeaderView(header: View) {
        headerList.remove(header)
        notifyDataSetChanged()
    }

    fun removeFooterView(footer: View) {
        footerList.remove(footer)
        notifyDataSetChanged()
    }

    fun getHeaderCount(): Int = headerList.size


    fun getFooterCount(): Int = footerList.size

    fun getHeaderViewAt(index: Int): View = headerList[index]


    fun getFooterViewAt(index: Int): View = footerList[index]

    override fun getItemViewType(position: Int): Int {
        if (position < getHeaderCount()) {
            return HEADER_TYPE + position
        } else if (position >= getHeaderCount() + getDataSize()) {
            return FOOTER_TYPE + position - getHeaderCount() - getDataSize()
        }
        return super.getItemViewType(position)
    }

    abstract fun createItemView(context: Context): View

    abstract fun bindItemView(viewHolder: CommonRecyclerViewHolder, position: Int, data: Data?)

    var itemClickListener: OnItemClickListener<Data>? = null

    interface OnItemClickListener<Data> {
        fun onItemClick(position: Int, data: Data)
    }
}