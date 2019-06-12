package com.xingzhe.ui_library.ptr

import com.xingzhe.ui_library.dataview.BaseObserver
import com.xingzhe.ui_library.dataview.DataRetryHandler

/**
 * Created by wumm on 2019/5/13.
 */
interface LoadMoreView {
    var retryHandler: DataRetryHandler?

    fun onLoadMoreError(error: BaseObserver.ResponseError)

    fun onLoading()

    fun onEnd()
}