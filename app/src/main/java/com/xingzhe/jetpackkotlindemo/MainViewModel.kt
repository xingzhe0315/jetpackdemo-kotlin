package com.xingzhe.jetpackkotlindemo

import com.xingzhe.http_library.BaseResponseData
import com.xingzhe.http_library.RetrofitClient
import io.reactivex.Observable

/**
 * Created by wumm on 2019/5/10.
 */
class MainViewModel: com.xingzhe.ui_library.dataview.BaseViewModel<AccountListEntity>() {
    override fun getObservable(): Observable<out BaseResponseData<*>> {
        return RetrofitClient.retrofit.create(ApiService::class.java).getAccountList()
    }
}