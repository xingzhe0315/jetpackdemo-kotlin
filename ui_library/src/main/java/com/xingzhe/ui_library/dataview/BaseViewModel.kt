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
                        onFetchSuccess(responses)
                    }

                    override fun onDataError(error: ResponseError): Boolean {
                        return onFetchError(error)
                    }

                })
    }

    open fun onFetchSuccess(responses: List<BaseResponseData<*>>) {
        liveData.value = getDataFromResponse(responses)
    }

    open fun onFetchError(error: BaseObserver.ResponseError): Boolean {
        errorLiveData.value = error
        return true
    }

    open fun getDataFromResponse(responses: List<BaseResponseData<*>>): Data? {
        return responses[0] as? Data
    }

}