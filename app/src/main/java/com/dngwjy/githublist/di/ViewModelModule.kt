package com.dngwjy.githublist.di

import com.dngwjy.githublist.ui.detail.DetailViewModel
import com.dngwjy.githublist.ui.detail.follower.FollowerViewModel
import com.dngwjy.githublist.ui.detail.following.FollowingViewModel
import com.dngwjy.githublist.ui.favourite.FavouriteViewModel
import com.dngwjy.githublist.ui.main.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val viewModelModule = module {
    single {
        MainViewModel(get())
    }
    single {
        DetailViewModel(get(),androidContext())
    }
    single {
        FollowerViewModel(get())
    }
    single {
        FollowingViewModel(get())
    }
    single {
        FavouriteViewModel(androidContext())
    }
}