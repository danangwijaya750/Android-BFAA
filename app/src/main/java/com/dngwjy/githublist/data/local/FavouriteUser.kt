package com.dngwjy.githublist.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dngwjy.githublist.domain.User


@Entity(tableName = "tbl_favourite")
data class FavouriteUser(
    val avatar_url: String,
    val events_url: String,
    val followers_url: String,
    val following_url: String,
    val gists_url: String,
    val gravatar_id: String,
    val html_url: String,
    @PrimaryKey
    val id: Int,
    val login: String,
    val node_id: String,
    val organizations_url: String,
    val received_events_url: String,
    val repos_url: String,
    val site_admin: Boolean,
    val starred_url: String,
    val subscriptions_url: String,
    val type: String,
    val url: String
){
    fun toUserDomain():User=
        User(
            avatar_url,
            events_url,
            followers_url,
            following_url,
            gists_url,
            gravatar_id,
            html_url,
            id,
            login,
            node_id,
            organizations_url,
            received_events_url,
            repos_url,
            site_admin,
            starred_url,
            subscriptions_url,
            type,
            url
        )
}