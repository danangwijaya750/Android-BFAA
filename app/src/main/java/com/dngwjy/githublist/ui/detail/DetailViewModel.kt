package com.dngwjy.githublist.ui.detail

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.viewModelScope
import com.dngwjy.githublist.abstraction.*
import com.dngwjy.githublist.data.repository.UserRepository
import com.dngwjy.githublist.domain.DetailUser
import com.dngwjy.githublist.util.logE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class DetailViewModel(private val repository: UserRepository) : BaseViewModel() {
    private lateinit var detailUser: DetailUser
    fun getUserDetail(username:String){
        if (this::detailUser.isInitialized){
            liveDataState.value=ShowDetailUser(detailUser)
            return
        }
        disposable.add(
            repository.getDetailUser(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    liveDataState.value=IsLoading(true)
                }
                .doOnComplete {
                    liveDataState.value=IsLoading(false)
                }
                .subscribe({
                    detailUser=it
                    liveDataState.value=ShowDetailUser(detailUser)
                },this::errorHandler)
        )


    }

    private fun errorHandler(t:Throwable){
        t.printStackTrace()
        liveDataState.postValue(IsError(t.localizedMessage))
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(){
        dispose()
    }

}