package com.juniorlanga.swapistarwars

import android.app.Application
import androidx.multidex.BuildConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SwapiStarWarsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        createTimber()
    }

    private fun createTimber() {
        if (BuildConfig.DEBUG) {

        }
    }
}