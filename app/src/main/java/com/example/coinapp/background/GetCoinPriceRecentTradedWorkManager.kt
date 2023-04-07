package com.example.coinapp.background

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import timber.log.Timber

// 최근 거래된 코인 가격 내역 가져오기
// 주기적으로 로컬 데이터를 네트워크와 동기화
class GetCoinPriceRecentTradedWorkManager(
    val context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        Timber.d("doWork")

        return Result.success()
    }

}