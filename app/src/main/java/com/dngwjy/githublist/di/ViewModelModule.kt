package com.dngwjy.githublist.di

import com.dngwjy.githublist.ui.main.MainViewModel
import org.koin.dsl.module

val viewModelModule= module {
    single {
        MainViewModel(get())
    }
}