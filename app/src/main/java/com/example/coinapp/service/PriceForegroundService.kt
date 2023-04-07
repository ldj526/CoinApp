package com.example.coinapp.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.coinapp.R
import com.example.coinapp.datamodel.CurrentPrice
import com.example.coinapp.datamodel.CurrentPriceResult
import com.example.coinapp.repository.NetWorkRepository
import com.example.coinapp.view.main.MainActivity
import com.google.gson.Gson
import kotlinx.coroutines.*
import timber.log.Timber
import java.util.*

class PriceForegroundService : Service() {

    private val netWorkRepository = NetWorkRepository()
    private val NOTIFICATION_ID = 10000

    lateinit var job: Job

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        when (intent?.action) {
            "START" -> {
                job = CoroutineScope(Dispatchers.Default).launch {
                    // 목록을 3초마다 변경해준다.
                    while (true) {
                        Timber.d("START")
                        startForeground(NOTIFICATION_ID, makeNotification())

                        delay(3000)
                    }
                }
            }
            "STOP" -> {
                Timber.d("STOP")
                try {
                    // job을 통해 coroutine을 바로 종료시켜준다.
                    job.cancel()
                    stopForeground(true)
                    stopSelf()
                } catch (e: java.lang.Exception) {

                }
            }
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    // Notification 띄우기
    suspend fun makeNotification(): Notification {

        // 모든 코인 정보를 가져온다.
        val result = getAllCoinList()
        // result 크기만큼의 수만큼 랜덤하게 뽑아준다.
        val randomNum = Random().nextInt(result.size)

        val title = result[randomNum].coinName
        val content = result[randomNum].coinInfo.fluctate_24H

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(this, "CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_baseline_access_alarms_24)
            .setContentTitle("코인 이름 : $title")
            .setContentText("변동 가격 : $content")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "name"
            val descriptionText = "descriptionText"
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        return builder.build()
    }

    suspend fun getAllCoinList(): ArrayList<CurrentPriceResult> {
        val result = netWorkRepository.getCurrentCoinList()

        val currentPriceResultList = ArrayList<CurrentPriceResult>()

        // Data Format 변환
        for (coin in result.data) {
            // API 에서 마지막 데이터의 포맷이 다르므로 예외처리를 해준다.
            try {
                val gson = Gson()
                val gsonToJson = gson.toJson(result.data[coin.key])
                val gsonFromJson = gson.fromJson(gsonToJson, CurrentPrice::class.java)

                val currentPriceResult = CurrentPriceResult(coin.key, gsonFromJson)

                currentPriceResultList.add(currentPriceResult)

            } catch (e: java.lang.Exception) {
                Timber.d(e.toString())
            }
        }
        return currentPriceResultList
    }
}