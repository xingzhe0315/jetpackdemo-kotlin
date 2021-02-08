package com.xingzhe.ui_library.dataview

import android.arch.lifecycle.MutableLiveData
import com.xingzhe.http_library.BaseResponseData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by wumm on 2019/5/13.
 */
abstract class BaseListViewModel<Data> : BaseViewModel<List<Data>>() {

    val headerDataList: MutableLiveData<List<BaseResponseData<*>>> by lazy {
        MutableLiveData<List<BaseResponseData<*>>>()
    }

    val appendDataList: MutableLiveData<List<Data>> by lazy {
        MutableLiveData<List<Data>>()
    }

    val loadMoreError: MutableLiveData<BaseObserver.ResponseError> by lazy {
        MutableLiveData<BaseObserver.ResponseError>()
    }

    var page: Int = 1

    fun refresh() {
        page = 1
        startLoad()
    }

    abstract fun getLoadMoreObservable(): Observable<out BaseResponseData<*>>

    fun loadMore() {
        getLoadMoreObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : BaseObserver<BaseResponseData<*>>() {
                    override fun onDataSuccess(responses: List<BaseResponseData<*>>) {
                        page++
                        appendData(getListDataFromResponse(responses[responses.size - 1]))
                    }

                    override fun onDataError(error: ResponseError): Boolean {
                        loadMoreError.value = error
                        return true
                    }

                })
    }

    override fun onFetchSuccess(responses: List<BaseResponseData<*>>) {
        super.onFetchSuccess(responses)
        headerDataList.value = getHeaderDataFromResponses(responses)
    }

    override fun getDataFromResponse(responses: List<BaseResponseData<*>>): List<Data>? {
        return getListDataFromResponse(responses[responses.size - 1])
    }

    open fun getHeaderDataFromResponses(responses: List<BaseResponseData<*>>): List<BaseResponseData<*>> {
        return responses.subList(0, responses.size - 1)
    }

    open fun getListDataFromResponse(response: BaseResponseData<*>): List<Data>? {
        return response.data as List<Data>
    }

    private fun appendData(dataList: List<Data>?) {
        appendDataList.value = dataList
    }
}