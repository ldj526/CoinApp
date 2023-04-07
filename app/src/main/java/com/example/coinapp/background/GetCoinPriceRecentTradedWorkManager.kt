package com.example.coinapp.background

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.coinapp.db.entity.SelectedCoinPriceEntity
import com.example.coinapp.network.model.RecentCoinPriceList
import com.example.coinapp.repository.DBRepository
import com.example.coinapp.repository.NetWorkRepository
import timber.log.Timber
import java.util.*

// 최근 거래된 코인 가격 내역 가져오기
// 주기적으로 로컬 데이터를 네트워크와 동기화
class GetCoinPriceRecentTradedWorkManager(
    val context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    private val dbRepository = DBRepository()
    private val netWorkRepository = NetWorkRepository()

    override suspend fun doWork(): Result {
        Timber.d("doWork")

        getAllInterestSelectedCoinData()

        return Result.success()
    }


    // 관심있어한 코인 데이터 가져오기
    suspend fun getAllInterestSelectedCoinData() {
        val selectedCoinList = dbRepository.getAllInterestSelectedCoinData()

        val timestamp = Calendar.getInstance().time

        for (coinData in selectedCoinList) {

            Timber.d(coinData.toString())

            // 관심있는 코인 각각의 가격 변동 정보 가져오기
            val recentCoinPriceList = netWorkRepository.getInterestCoinPriceData(coinData.coin_name)

            Timber.d(recentCoinPriceList.toString())

            saveSelectedCoinPrice(coinData.coin_name, recentCoinPriceList, timestamp)

        }
    }

    // 관심있는 코인 각각의 가격 변동 정보 DB에 저장
    fun saveSelectedCoinPrice(
        coinName: String,
        recentCoinPriceList: RecentCoinPriceList,
        timestamp: Date
    ) {
        val selectedCoinPriceEntity = SelectedCoinPriceEntity(
            0,
            coinName,
            recentCoinPriceList.data[0].transaction_date,
            recentCoinPriceList.data[0].type,
            recentCoinPriceList.data[0].units_traded,
            recentCoinPriceList.data[0].price,
            recentCoinPriceList.data[0].total,
            timestamp
        )
        // db에 저장
        dbRepository.insertCoinPriceData(selectedCoinPriceEntity)
    }

}