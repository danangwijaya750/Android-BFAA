package com.dngwjy.githublist

import android.app.Application
import com.dngwjy.githublist.di.networkModule
import com.dngwjy.githublist.di.repositoryModule
import com.dngwjy.githublist.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(networkModule, repositoryModule, viewModelModule))
        }
    }
}