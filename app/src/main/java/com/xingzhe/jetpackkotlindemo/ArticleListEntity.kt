package com.xingzhe.jetpackkotlindemo

import com.xingzhe.http_library.BaseResponseData

/**
 * Created by wumm on 2019/5/10.
 */
class ArticleListEntity : BaseResponseData<ArticleListEntity.Article>() {

    class Article {
        var datas: ArrayList<ArticleItem>? = null
    }

    class ArticleItem {
        var title: String? = null
    }

}