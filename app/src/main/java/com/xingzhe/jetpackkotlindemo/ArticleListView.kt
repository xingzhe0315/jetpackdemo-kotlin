package com.xingzhe.jetpackkotlindemo

import android.content.Context
import android.view.View
import com.xingzhe.ui_library.dataview.PtrListDataView
import com.xingzhe.ui_library.dataview.ViewModelCreator
import com.xingzhe.ui_library.recyclerview.BaseRecyclerAdapter
import com.xingzhe.ui_library.recyclerview.CommonRecyclerViewHolder
import com.xingzhe.ui_library.viewpager.Page

/**
 * Created by wumm on 2019/5/10.
 */
class ArticleListView(context: Context) : PtrListDataView<ArticleListEntity.ArticleItem, ArticleListViewModel>(context), Page {
    override fun createAdapter(): BaseRecyclerAdapter<ArticleListEntity.ArticleItem> {
        return MainAdapter()
    }

    var accountId: String = ""
        set(value) {
            field = value
            getViewModel().id = value
        }

    override fun createViewModel(): ArticleListViewModel {
        return ViewModelCreator.createViewModel(context, ArticleListViewModel::class.java)
    }

    override fun onPageInit() {
        startLoad()
    }

    override fun onPageShow() {
    }

    override fun onPageHide() {
    }


    class MainAdapter : BaseRecyclerAdapter<ArticleListEntity.ArticleItem>() {
        override fun createItemView(context: Context): View {
            return View.inflate(context, R.layout.view_item_view, null)
        }

        override fun bindItemView(viewHolder: CommonRecyclerViewHolder, position: Int, data: ArticleListEntity.ArticleItem?) {
            viewHolder.setText(R.id.account_name, data?.title)
        }

    }


}