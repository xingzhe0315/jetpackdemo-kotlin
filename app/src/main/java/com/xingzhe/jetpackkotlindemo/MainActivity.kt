package com.xingzhe.jetpackkotlindemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.xingzhe.jetpackkotlindemo.base.BaseActivity
import com.xingzhe.jetpackkotlindemo.base.TaskManager
import com.xingzhe.ui_library.dataview.ViewModelCreator
import com.xingzhe.ui_library.dataview.SimpleDataView
import com.xingzhe.ui_library.viewpager.BasePagerAdapter
import com.xingzhe.ui_library.viewpager.XZViewPager

class MainActivity : BaseActivity() {
    override fun initDataFromIntent(intent: Intent) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainView = MainView(this)
        setContentView(mainView)
        mainView.startLoad()
    }

    class MainView(context: Context) : SimpleDataView<AccountListEntity, MainViewModel>(context) {
        private var adapter: AccountPagerAdapter? = null

        override fun createViewModel(): MainViewModel {
            return com.xingzhe.ui_library.dataview.ViewModelCreator.createViewModel(context,MainViewModel::class.java)
        }

        override fun createView(context: Context): View {
            val view = View.inflate(context, R.layout.activity_main, null)
            val accountTab: TabLayout = view.findViewById(R.id.account_tab_layout)
            val viewPager: XZViewPager = view.findViewById(R.id.account_pager)
            adapter = AccountPagerAdapter()
            viewPager.adapter = adapter
            accountTab.setupWithViewPager(viewPager)
            viewPager.showPage(0)
            view.findViewById<Button>(R.id.header_list).setOnClickListener {
                TaskManager.executeAfterChooseCategory(context as BaseActivity,object :TaskManager.TaskAction{
                    override fun action(data: Intent?) {
                        Toast.makeText(context,data?.getStringExtra("name"),Toast.LENGTH_LONG).show()
                    }
                })
            }
            return view
        }

        override fun bindView(data: AccountListEntity) {
            adapter!!.accountList = data.data as MutableList<AccountListEntity.Account>
        }

    }

    class AccountPagerAdapter : BasePagerAdapter() {
        var accountList: MutableList<AccountListEntity.Account>? = null
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun createItemView(context: Context, position: Int): View {
            val articleListView = ArticleListView(context)
            articleListView.accountId = accountList!![position].id.toString()
            return articleListView
        }

        override fun getCount(): Int {
            return if (accountList == null) 0 else accountList!!.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return accountList!![position].name
        }

    }

}
