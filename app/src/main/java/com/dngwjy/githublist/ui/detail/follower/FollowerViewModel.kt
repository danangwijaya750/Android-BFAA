package com.dngwjy.githublist.ui.detail.follower

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.dngwjy.githublist.abstraction.BaseViewModel
import com.dngwjy.githublist.abstraction.IsError
import com.dngwjy.githublist.abstraction.IsLoading
import com.dngwjy.githublist.abstraction.ShowFollowers
import com.dngwjy.githublist.data.repository.UserRepository
import com.dngwjy.githublist.domain.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FollowerViewModel(private val repository: UserRepository) : BaseViewModel() {
    private lateinit var listFollower: MutableList<User>

    fun getFollowers(username: String) {
        listFollower = mutableListOf()
        disposable.add(
            repository.getFollowers(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { liveDataState.value = IsLoading(true) }
                .doOnComplete { liveDataState.value = IsLoading(false) }
                .subscribe({
                    listFollower.clear()
                    listFollower.addAll(it)
                    liveDataState.value = ShowFollowers(listFollower)
                }, this::errorHandler)
        )
    }

    private fun errorHandler(t: Throwable) {
        t.printStackTrace()
        liveDataState.postValue(IsError(t.localizedMessage))
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        dispose()
    }

}