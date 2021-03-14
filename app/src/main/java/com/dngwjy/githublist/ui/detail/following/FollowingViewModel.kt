package com.dngwjy.githublist.ui.detail.following

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.viewModelScope
import com.dngwjy.githublist.abstraction.*
import com.dngwjy.githublist.data.repository.UserRepository
import com.dngwjy.githublist.domain.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FollowingViewModel(private val repository: UserRepository):BaseViewModel() {
    private lateinit var listFollowing:MutableList<User>

    fun getFollowing(username:String){
        if(this::listFollowing.isInitialized){
            liveDataState.value=ShowFollowing(listFollowing)
            return
        }
        listFollowing= mutableListOf()
       disposable.add(
           repository.getFollowing(username)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .doOnSubscribe { liveDataState.value=IsLoading(true)  }
               .doOnComplete { liveDataState.value=IsLoading(false) }
               .subscribe({
                   listFollowing.clear()
                   listFollowing.addAll(it)
                   liveDataState.value=ShowFollowing(listFollowing)
               },this::errorHandler)
       )
    }
    private fun errorHandler(t:Throwable){
        t.printStackTrace()
        liveDataState.value = IsError(t.localizedMessage)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(){
        dispose()
    }

}