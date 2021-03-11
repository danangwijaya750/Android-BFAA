package com.dngwjy.githublist.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class Data(
    val users: List<User>
)

@Parcelize
data class User(
    val avatar: String,
    val company: String,
    val follower: Int,
    val following: Int,
    val location: String,
    val name: String,
    val repository: Int,
    val username: String
) : Parcelable