package com.xingzhe.jetpackkotlindemo

import com.xingzhe.http_library.BaseResponseData
import com.xingzhe.http_library.RetrofitClient
import io.reactivex.Observable

/**
 * Created by wumm on 2019/5/10.
 */
class ArticleListViewModel: com.xingzhe.ui_library.dataview.BaseListViewModel<ArticleListEntity.ArticleItem>() {
    override fun getLoadMoreObservable(): Observable<out BaseResponseData<*>> {
        return getObservable()
    }

    var id:String=""
    override fun getObservable(): Observable<out BaseResponseData<*>> {
        return RetrofitClient.retrofit.create(ApiService::class.java).getArticalList(id,page)
    }

    override fun getListDataFromResponse(response: BaseResponseData<*>): List<ArticleListEntity.ArticleItem>? {
        return (response.data as ArticleListEntity.Article).datas
    }

}