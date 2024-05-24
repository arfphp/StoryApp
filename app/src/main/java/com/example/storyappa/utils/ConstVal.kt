package com.example.storyappa.utils

import com.example.storyappa.di.networkModule
import com.example.storyappa.di.preferenceModule
import com.example.storyappa.BuildConfig
import com.example.storyappa.R
import com.example.storyappa.di.feature.authModule
import com.example.storyappa.di.feature.storyModule
import com.example.storyappa.di.networkModule
import com.example.storyappa.di.preferenceModule
import com.example.storyappa.di.viewModelModule

object ConstVal {
    // Koin Modules
    val koinModules = listOf(
        networkModule,
        preferenceModule,
        viewModelModule,
        authModule,
        storyModule,
    )

    // Navigation Bar Destination
    val navBarDestination = listOf(R.id.homeFragment, R.id.mapFragment, R.id.settingsFragment)

    const val SPLASH_SCREEN_DURATION = 3
    const val BASE_URL = BuildConfig.BASE_URL

    // Shared Preferences
    const val PREFS_NAME = "storystory.pref"
    const val KEY_TOKEN = "key.token"
    const val KEY_NAME = "key.name"

    const val MAXIMAL_SIZE = 1000000 // 1 MB
    const val FILENAME_FORMAT = "yyyyMMdd_HHmmss"
}