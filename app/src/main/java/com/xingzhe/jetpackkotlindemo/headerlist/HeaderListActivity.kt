package com.xingzhe.jetpackkotlindemo.headerlist

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.util.AttributeSet
import android.view.View
import com.xingzhe.http_library.BaseResponseData
import com.xingzhe.http_library.RetrofitClient
import com.xingzhe.jetpackkotlindemo.AccountListEntity
import com.xingzhe.jetpackkotlindemo.ApiService
import com.xingzhe.jetpackkotlindemo.ArticleListEntity
import com.xingzhe.jetpackkotlindemo.R
import com.xingzhe.jetpackkotlindemo.base.BaseActivity
import com.xingzhe.ui_library.dataview.BaseListViewModel
import com.xingzhe.ui_library.dataview.PtrHeaderListDataView
import com.xingzhe.ui_library.recyclerview.BaseRecyclerAdapter
import com.xingzhe.ui_library.recyclerview.CommonRecyclerViewHolder
import io.reactivex.Observable

class HeaderListActivity : BaseActivity() {
    override fun initDataFromIntent(intent: Intent) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(MainHeaderListView(this))
    }

    class MainHeaderListView(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
            PtrHeaderListDataView<ArticleListEntity.ArticleItem, MainHeaderListViewModel>(context, attrs, defStyle) {
        init {
            startLoad()
        }

        override fun getHeaderCount(): Int {
            return 1
        }

        override fun createHeaderView(position: Int): View {
            val tabLayout = TabLayout(context)
            tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
            return tabLayout
        }

        override fun bindHeaderView(position: Int, data: BaseResponseData<*>) {
            val tabLayout = adapter.getHeaderViewAt(0) as TabLayout
            if (data is AccountListEntity) {
                val accountList = data.data ?: return
                for (i in 0 until accountList.size) {
                    val newTab = tabLayout.newTab()
                    newTab.text = accountList[i].name
                    tabLayout.addTab(newTab)
                }
            }

        }

        override fun createListAdapter(): BaseRecyclerAdapter<ArticleListEntity.ArticleItem> {
            val adapter = ArticleAdapter()
            adapter.itemClickListener = object : BaseRecyclerAdapter.OnItemClickListener<ArticleListEntity.ArticleItem> {
                override fun onItemClick(position: Int, data: ArticleListEntity.ArticleItem) {
                    val intent = Intent()
                    intent.putExtra("name", data.title)
                    (context as Activity).setResult(Activity.RESULT_OK, intent)
                    (context as Activity).finish()
                }

            }
            return adapter
        }

        override fun createViewModel(): MainHeaderListViewModel {
            return com.xingzhe.ui_library.dataview.ViewModelCreator.createViewModel(context, MainHeaderListViewModel::class.java)
        }
    }


    class ArticleAdapter : BaseRecyclerAdapter<ArticleListEntity.ArticleItem>() {
        override fun createItemView(context: Context): View {
            return View.inflate(context, R.layout.view_item_view, null)
        }

        override fun bindItemView(
                viewHolder: CommonRecyclerViewHolder,
                position: Int,
                data: ArticleListEntity.ArticleItem?
        ) {
            viewHolder.setText(R.id.account_name, data?.title)
        }

    }

    class MainHeaderListViewModel : BaseListViewModel<ArticleListEntity.ArticleItem>() {
        var accountId: String = ""
        override fun getLoadMoreObservable(): Observable<out BaseResponseData<*>> {
            return RetrofitClient.retrofit.create(ApiService::class.java).getArticalList(accountId, page)
        }

        override fun getObservable(): Observable<out BaseResponseData<*>> {
            val accountList = RetrofitClient.retrofit.create(ApiService::class.java).getAccountList()
            return accountList.flatMap {
                accountId = it.data?.getOrNull(0)?.id?.toString() ?: ""
                Observable.concat(accountList, RetrofitClient.retrofit.create(ApiService::class.java).getArticalList(accountId, 1))
            }
        }

        override fun getListDataFromResponse(response: BaseResponseData<*>): List<ArticleListEntity.ArticleItem>? {
            return (response as ArticleListEntity).data?.datas
        }

    }
}
