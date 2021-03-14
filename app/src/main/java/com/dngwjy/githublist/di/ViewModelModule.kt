package com.dngwjy.githublist.di

import com.dngwjy.githublist.ui.detail.DetailViewModel
import com.dngwjy.githublist.ui.detail.follower.FollowerViewModel
import com.dngwjy.githublist.ui.detail.following.FollowingViewModel
import com.dngwjy.githublist.ui.main.MainViewModel
import org.koin.dsl.module

val viewModelModule= module {
    single {
        MainViewModel(get())
    }
    single{
        DetailViewModel(get())
    }
    single{
        FollowerViewModel(get())
    }
    single {
        FollowingViewModel(get())
    }
}