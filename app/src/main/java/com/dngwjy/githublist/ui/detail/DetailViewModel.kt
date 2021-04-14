package com.dngwjy.githublist.ui.detail

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.dngwjy.githublist.abstraction.*
import com.dngwjy.githublist.data.datasource.FavouriteDatabase
import com.dngwjy.githublist.data.local.FavouriteUser
import com.dngwjy.githublist.data.repository.UserRepository
import com.dngwjy.githublist.domain.DetailUser
import com.dngwjy.githublist.util.logE
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailViewModel(private val repository: UserRepository,context:Context) : BaseViewModel() {
    private lateinit var detailUser: DetailUser
    private val db = FavouriteDatabase.getInstance(context)

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

    fun addFavourite(favouriteUser: FavouriteUser){
        disposable.add(
            Observable.fromCallable {
                db.favDao().insert(favouriteUser)
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete {
                    liveDataState.value=IsSuccessAdd(true)
                }
                .subscribe()
        )
    }
    fun removeFavourite(favouriteUser: FavouriteUser){
        disposable.add(
            Observable.fromCallable {
                db.favDao().removeFavourite(favouriteUser)
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete {
                    liveDataState.value=IsSuccessRemove(true)
                }
                .subscribe()
        )
    }
    fun checkFavourite(id:Int){
        disposable.add(
            db.favDao().checkData(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe {
                    logE("size it ${it.size}")
                    if (it.isNotEmpty()) {
                        liveDataState.value = IsFavourite(true)
                    } else {
                        liveDataState.value = IsFavourite(false)
                    }
                }
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