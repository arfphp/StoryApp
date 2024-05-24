package com.example.storyappa.di.feature

import com.example.storyappa.data.story.StoryRepositoryImpl
import org.koin.dsl.module

val storyModule = module {
    single { StoryRepositoryImpl(get()) }
}