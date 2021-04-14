package com.dngwjy.githublist.abstraction

import com.dngwjy.githublist.data.local.FavouriteUser
import com.dngwjy.githublist.domain.DetailUser
import com.dngwjy.githublist.domain.User

sealed class LiveDataState

data class IsLoading(val state: Boolean) : LiveDataState()
data class IsError(val msg: String) : LiveDataState()
data class ShowUser(val data: List<User>) : LiveDataState()
data class ShowDetailUser(val data: DetailUser) : LiveDataState()
data class ShowSearchUser(val data: List<User>) : LiveDataState()
data class ShowFollowers(val data: List<User>) : LiveDataState()
data class ShowFollowing(val data: List<User>) : LiveDataState()
data class ShowFavourites(val data: List<User>):LiveDataState()
data class IsSuccessAdd(val state: Boolean):LiveDataState()
data class IsSuccessRemove(val state: Boolean):LiveDataState()
data class IsFavourite(val state: Boolean):LiveDataState()
