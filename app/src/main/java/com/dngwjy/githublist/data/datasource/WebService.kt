package com.dngwjy.githublist.data.datasource

import com.dngwjy.githublist.domain.DetailUser
import com.dngwjy.githublist.domain.SearchResponse
import com.dngwjy.githublist.domain.User
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WebService {
    @GET("users")
    fun getUsers(): Observable<List<User>>
    @GET("users/{username}")
    fun getDetailUser(@Path("username") username:String):Observable<DetailUser>
    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username:String):Observable<List<User>>
    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username:String):Observable<List<User>>
    @GET("search/users")
    fun getSearchUser(@Query("q") username:String):Observable<SearchResponse>
}