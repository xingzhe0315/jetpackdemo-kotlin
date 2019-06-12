package com.xingzhe.http_library

/**
 * Created by wumm on 2019/5/9.
 */
open class BaseResponseData<T> {
    var errorCode: Int = 0
    var errorMsg: String = ""
    var data: T? = null
}