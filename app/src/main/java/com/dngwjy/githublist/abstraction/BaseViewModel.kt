package com.dngwjy.githublist.abstraction

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    protected val disposable = CompositeDisposable()
    val liveDataState = MutableLiveData<LiveDataState>()

    protected fun dispose() {
        if (!disposable.isDisposed) disposable.dispose()
    }
}