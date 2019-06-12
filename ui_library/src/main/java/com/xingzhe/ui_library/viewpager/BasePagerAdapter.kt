package com.xingzhe.ui_library.viewpager

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup

/**
 * Created by wumm on 2019/5/10.
 */
abstract class BasePagerAdapter : PagerAdapter() {
    private var cachedView:SparseArray<View> = SparseArray()

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val cachedViewAt = getCachedViewAt(container.context, position)
        if (cachedViewAt.parent == null) {
            container.addView(cachedViewAt)
        }
        return cachedViewAt
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }

    override fun isViewFromObject(view: View, any: Any): Boolean {
        return any == view
    }

    fun getCachedViewAt(context:Context,position:Int):View{
        var itemView = cachedView.get(position)
        if (itemView == null){
            itemView = createItemView(context,position)
            cachedView.put(position,itemView)
        }
        return itemView
    }

    abstract fun createItemView(context: Context, position: Int): View
}