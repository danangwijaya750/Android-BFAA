package com.dngwjy.githublist.ui.favourite

import android.content.Context
import com.dngwjy.githublist.abstraction.BaseViewModel
import com.dngwjy.githublist.abstraction.ShowFavourites
import com.dngwjy.githublist.data.datasource.FavouriteDatabase
import com.dngwjy.githublist.domain.User
import com.dngwjy.githublist.util.logE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FavouriteViewModel(context: Context):BaseViewModel() {
    private val db = FavouriteDatabase.getInstance(context)
    fun getFavourite(){
        val userList = mutableListOf<User>()
        disposable.add(
            db.favDao().getData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnComplete {
                }
                .subscribe({
                    userList.clear()
                    it.forEach { fav->
                        userList.add(fav.toUserDomain())
                    }
                    liveDataState.postValue(ShowFavourites(userList))
                },this::onError
                )
        )
    }
    private fun onError(t:Throwable){
        logE(t.localizedMessage)
    }
}