package com.xingzhe.jetpackkotlindemo

import com.xingzhe.http_library.BaseResponseData

/**
 * Created by wumm on 2019/5/9.
 */
class AccountListEntity : BaseResponseData<List<AccountListEntity.Account>>() {

    class Account {
        var courseId: Int = 0
        var id: Int = 0
        var name: String? = null
        var order: Int = 0
        var parentChapterId: Int = 0
        var userControlSetTop: Boolean = false
        var visible: Int = 0
    }
}