package com.xingzhe.ui_library.dataview

import com.xingzhe.http_library.BaseResponseData
import io.reactivex.Observable

/**
 * Created by wumm on 2019/5/13.
 */
class EmptyObservableCreator {
    companion object {
        fun createEmptyObservable():Observable<BaseResponseData<*>>{
            return Observable.just(null)
        }
    }
}