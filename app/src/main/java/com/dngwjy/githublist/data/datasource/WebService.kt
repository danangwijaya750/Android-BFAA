package com.dngwjy.githublist.data.datasource

import com.dngwjy.githublist.domain.DetailUser
import com.dngwjy.githublist.domain.SearchResponse
import com.dngwjy.githublist.domain.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WebService {
    @GET("users")
    suspend fun getUsers():List<User>
    @GET("users/{username}")
    suspend fun getDetailUser(@Path("username") username:String):DetailUser
    @GET("users/{username}/followers")
    suspend fun getFollowers(@Path("username") username:String):List<User>
    @GET("users/{username}/following")
    suspend fun getFollowing(@Path("username") username:String):List<User>
    @GET("search/users")
    suspend fun getSearchUser(@Query("q") username:String):SearchResponse
}