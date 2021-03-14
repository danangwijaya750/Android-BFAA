package com.dngwjy.githublist.abstraction

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel:ViewModel() {
    protected val disposable = CompositeDisposable()
    val liveDataState= MutableLiveData<LiveDataState>()

    protected fun dispose(){
        if(!disposable.isDisposed) disposable.dispose()
    }
}