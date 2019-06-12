package com.xingzhe.ui_library.dataview

import com.xingzhe.http_library.BaseResponseData
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Created by wumm on 2019/5/9.
 */
abstract class BaseObserver<Data: BaseResponseData<*>> : Observer<Data> {
    private var disposable: Disposable? = null
    private var responseList: MutableList<BaseResponseData<*>>? = ArrayList()

    override fun onComplete() {
        disposable?.dispose()
        onDataSuccess(responseList!!)
    }

    override fun onSubscribe(d: Disposable) {
        disposable = d
    }

    override fun onNext(response: Data) {
        if (response.errorCode == 0){
            responseList?.add(response)
        } else {
            responseList?.clear()
            responseList = null
            onDataError(
                ResponseError(
                    response.errorCode,
                    response.errorMsg
                )
            )
            disposable?.dispose()
        }
    }

    override fun onError(e: Throwable) {
        disposable?.dispose()
        onDataError(ResponseError(-100, e.message))
    }

    abstract fun onDataSuccess(responses:List<BaseResponseData<*>>)

    abstract fun onDataError(error: ResponseError):Boolean

    class ResponseError (errorCode:Int, errorMessage: String?){
        var errorCode:Int = 0
        var errorMessage:String?=""
        init {
            this.errorCode = errorCode
            this.errorMessage = errorMessage
        }
    }
}