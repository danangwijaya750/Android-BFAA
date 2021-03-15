package com.dngwjy.githublist.ui.detail

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.dngwjy.githublist.abstraction.BaseViewModel
import com.dngwjy.githublist.abstraction.IsError
import com.dngwjy.githublist.abstraction.IsLoading
import com.dngwjy.githublist.abstraction.ShowDetailUser
import com.dngwjy.githublist.data.repository.UserRepository
import com.dngwjy.githublist.domain.DetailUser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailViewModel(private val repository: UserRepository) : BaseViewModel() {
    private lateinit var detailUser: DetailUser
    fun getUserDetail(username: String) {
        disposable.add(
            repository.getDetailUser(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    liveDataState.value = IsLoading(true)
                }
                .doOnComplete {
                    liveDataState.value = IsLoading(false)
                }
                .subscribe({
                    detailUser = it
                    liveDataState.value = ShowDetailUser(detailUser)
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