package com.example.storyappa.di.feature

import com.example.storyappa.data.auth.AuthRepositoryImpl
import org.koin.dsl.module

val authModule = module {
    single { AuthRepositoryImpl(get(), get()) }
}