package com.dngwjy.githublist.ui.main

import androidx.lifecycle.viewModelScope
import com.dngwjy.githublist.abstraction.BaseViewModel
import com.dngwjy.githublist.abstraction.IsError
import com.dngwjy.githublist.abstraction.IsLoading
import com.dngwjy.githublist.abstraction.ShowSearchUser
import com.dngwjy.githublist.data.repository.UserRepository
import com.dngwjy.githublist.domain.User
import com.dngwjy.githublist.util.logE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val repository: UserRepository):BaseViewModel() {
    lateinit var userList:MutableList<User>

    fun searchUser(q:String){
        if(this::userList.isInitialized){
            liveDataState.value=ShowSearchUser(userList)
            return
        }
        liveDataState.value=IsLoading(true)
        userList= mutableListOf()
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                try{
                    val result =repository.searchUser(q)
                    userList.clear()
                    userList.addAll(result.items)
                    liveDataState.postValue(IsLoading(false))
                    if(userList.isEmpty()){
                        liveDataState.postValue(IsError("User Not Exist"))
                        liveDataState.postValue(ShowSearchUser(userList))
                    }else{
                        liveDataState.postValue(ShowSearchUser(userList))
                    }
                }catch (t:Throwable){
                    handleError(t)
                }
            }
        }
    }

    private fun handleError(t:Throwable){
        t.printStackTrace()
        logE(t.localizedMessage)
    }
}