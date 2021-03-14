package com.dngwjy.githublist.ui.main

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.dngwjy.githublist.abstraction.BaseViewModel
import com.dngwjy.githublist.abstraction.IsError
import com.dngwjy.githublist.abstraction.IsLoading
import com.dngwjy.githublist.abstraction.ShowSearchUser
import com.dngwjy.githublist.data.repository.UserRepository
import com.dngwjy.githublist.domain.User
import com.dngwjy.githublist.util.logE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val repository: UserRepository) : BaseViewModel() {
    lateinit var userList: MutableList<User>
    var oldSearch = ""
    fun searchUser(q: String) {
        if (this::userList.isInitialized && q == oldSearch) {
            liveDataState.value = ShowSearchUser(userList)
            return
        }
        oldSearch = q
        userList = mutableListOf()
        disposable.add(
            repository.searchUser(q)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    liveDataState.value = IsLoading(true)
                }
                .doOnComplete {
                    liveDataState.value = IsLoading(false)
                }
                .subscribe({
                    this.userList.clear()
                    this.userList.addAll(it.items)
                    liveDataState.value = ShowSearchUser(this.userList)
                    if (userList.size == 0) {
                        liveDataState.value = IsError("User Not Found")
                    }
                }, this::handleError)
        )
    }

    private fun handleError(t: Throwable) {
        t.printStackTrace()
        logE(t.localizedMessage)
        liveDataState.value = IsError(t.localizedMessage)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        dispose()
    }

}