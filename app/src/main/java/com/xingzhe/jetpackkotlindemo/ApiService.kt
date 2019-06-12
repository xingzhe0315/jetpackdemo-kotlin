package com.xingzhe.jetpackkotlindemo

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by wumm on 2019/5/9.
 */
interface ApiService {
    @GET("wxarticle/chapters/json")
    fun getAccountList() : Observable<AccountListEntity>

    @GET("wxarticle/list/{id}/{page}/json")
    fun getArticalList(@Path("id") accountId: String, @Path("page") page: Int): Observable<ArticleListEntity>
}