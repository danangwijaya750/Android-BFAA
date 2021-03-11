package com.dngwjy.githublist.di

import com.dngwjy.githublist.data.repository.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    single {
        UserRepository(get())
    }
}