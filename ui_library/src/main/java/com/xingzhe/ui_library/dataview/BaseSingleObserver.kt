package com.xingzhe.ui_library.dataview

import com.xingzhe.http_library.BaseResponseData


/**
 * Created by wumm on 2019/5/9.
 */
abstract class BaseSingleObserver<Data: BaseResponseData<*>>: com.xingzhe.ui_library.dataview.BaseObserver<Data>() {
    override fun onDataSuccess(responses: List<BaseResponseData<*>>) {
        onDataSuccess(responses[0] as Data)
    }

    abstract fun onDataSuccess(response:Data)
}