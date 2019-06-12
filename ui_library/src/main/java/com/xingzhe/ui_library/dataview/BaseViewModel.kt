package com.xingzhe.ui_library.dataview

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.xingzhe.http_library.BaseResponseData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by wumm on 2019/5/9.
 */
abstract class BaseViewModel<Data> : ViewModel() {
    val liveData: MutableLiveData<Data> by lazy {
        MutableLiveData<Data>()
    }

    val errorLiveData: MutableLiveData<BaseObserver.ResponseError> by lazy {
        MutableLiveData<BaseObserver.ResponseError>()
    }

    abstract fun getObservable(): Observable<out BaseResponseData<*>>

    fun startLoad() {
        getObservable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserver<BaseResponseData<*>>() {
                override fun onDataSuccess(responses: List<BaseResponseData<*>>) {
                    getDataFromObserver(responses)
                }

                override fun onDataError(error: ResponseError): Boolean {
                    errorLiveData.value = error
                    return true
                }

            })
    }

    open fun getDataFromObserver(responses: List<BaseResponseData<*>>) {
        liveData.value = getDataFromResponse(responses)
    }

    open fun getDataFromResponse(responses: List<BaseResponseData<*>>): Data? {
        return if (isSingle()) getSingleDataFromResponse(responses[0]) else getMultiDataFromResponses(responses)
    }

    protected open fun getSingleDataFromResponse(response: BaseResponseData<*>): Data? {
        return response as Data
    }

    protected open fun getMultiDataFromResponses(responses: List<BaseResponseData<*>>): Data? {
        return null
    }

    protected open fun isSingle(): Boolean {
        return true
    }

}