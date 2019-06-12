package com.xingzhe.ui_library.recyclerview

import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.View
import android.widget.TextView

/**
 * Created by wumm on 2019/5/10.
 */
class CommonRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var cachedView: SparseArray<View> = SparseArray()
    fun getView(id: Int): View? {
        var view = cachedView.get(id)
        if (view == null) {
            view = itemView.findViewById<View>(id)
            cachedView.put(id, view)
        }
        return view
    }

    fun setText(id: Int, text: CharSequence?) {
        val view: TextView? = getView(id) as TextView?
        view?.text = text
    }

    fun setText(id: Int, resId: Int) {
        val view: TextView? = getView(id) as TextView?
        view?.setText(resId)
    }
}