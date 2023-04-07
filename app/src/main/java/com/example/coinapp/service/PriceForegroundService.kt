package com.example.coinapp.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import timber.log.Timber

class PriceForegroundService : Service() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        when (intent?.action) {
            "START" -> {
                Timber.d("START")
            }
            "STOP" -> {
                Timber.d("STOP")
            }
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}