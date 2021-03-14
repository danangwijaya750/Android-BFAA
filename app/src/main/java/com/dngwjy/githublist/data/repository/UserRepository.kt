package com.dngwjy.githublist.data.repository

import com.dngwjy.githublist.data.datasource.WebService

class UserRepository(private val service: WebService) {
    fun getUsers() = service.getUsers()
    fun searchUser(q: String) = service.getSearchUser(q)
    fun getFollowers(username: String) = service.getFollowers(username)
    fun getFollowing(username: String) = service.getFollowing(username)
    fun getDetailUser(username: String) = service.getDetailUser(username)
}