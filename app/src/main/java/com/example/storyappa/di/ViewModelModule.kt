package com.example.storyappa.di

import com.example.storyappa.ui.add.AddStoryViewModel
import com.example.storyappa.ui.detail.DetailStoryViewModel
import com.example.storyappa.ui.home.HomeViewModel
import com.example.storyappa.ui.login.LoginViewModel
import com.example.storyappa.ui.register.RegisterViewModel
import com.example.storyappa.ui.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { AddStoryViewModel(get()) }
    viewModel { DetailStoryViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
}