package com.example.strengthennumber

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class StrengthenNumber : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("APP", "Strengthen Number")
    }
}