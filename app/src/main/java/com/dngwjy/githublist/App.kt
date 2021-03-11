package com.dngwjy.githublist

import android.app.Application
import com.dngwjy.githublist.di.networkModule
import com.dngwjy.githublist.di.repositoryModule
import com.dngwjy.githublist.di.viewModelModule
import org.koin.core.context.startKoin

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(networkModule, repositoryModule, viewModelModule))
        }
    }
}