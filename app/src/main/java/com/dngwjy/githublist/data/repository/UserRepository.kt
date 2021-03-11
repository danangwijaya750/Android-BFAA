package com.dngwjy.githublist.data.repository

import com.dngwjy.githublist.data.datasource.WebService

class UserRepository(private val service: WebService) {
    suspend fun getUsers() = service.getUsers()
    suspend fun searchUser(q: String) = service.getSearchUser(q)
    suspend fun getFollowers(username: String) = service.getFollowers(username)
    suspend fun getFollowing(username: String) = service.getFollowing(username)
    suspend fun getDetailUser(username: String) = service.getDetailUser(username)
}