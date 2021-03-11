package com.dngwjy.githublist.abstraction

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel:ViewModel() {
    val liveDataState= MutableLiveData<LiveDataState>()
}