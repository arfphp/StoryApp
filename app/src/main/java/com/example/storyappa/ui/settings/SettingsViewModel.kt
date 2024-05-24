package com.example.storyappa.ui.settings

import androidx.lifecycle.ViewModel
import com.example.storyappa.data.auth.AuthRepositoryImpl

class SettingsViewModel(private val authRepositoryImpl: AuthRepositoryImpl) : ViewModel() {
    fun logout() = authRepositoryImpl.logout()
}