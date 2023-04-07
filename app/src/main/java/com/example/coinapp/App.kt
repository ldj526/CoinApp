package com.example.coinapp

import android.app.Application
import android.content.Context
import timber.log.Timber

class App : Application() {

    init {
        instance = this
    }

    // Context 쓰기 위함
    companion object {
        private var instance: App? = null
        fun context(): Context {
            return instance!!.applicationContext
        }
    }


    // Log 찍기 위함
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}