package com.example.coinapp.repository

import com.example.coinapp.App
import com.example.coinapp.db.CoinPriceDatabase
import com.example.coinapp.db.entity.InterestCoinEntity
import com.example.coinapp.db.entity.SelectedCoinPriceEntity

class DBRepository {

    val context = App.context()
    val db = CoinPriceDatabase.getDatabase(context)

    // InterestCoin

    // 전체 코인 데이터 가져오기
    fun getAllInterestCoinData() = db.interestCoinDao().getAllData()

    // 코인 데이터 넣기
    fun insertInterestCoinData(interestCoinEntity: InterestCoinEntity) =
        db.interestCoinDao().insert(interestCoinEntity)

    // 코인 데이터 업데이트
    fun updateInterestCoinData(interestCoinEntity: InterestCoinEntity) =
        db.interestCoinDao().update(interestCoinEntity)

    // 관심있는 코인만 가져오기
    fun getAllInterestSelectedCoinData() = db.interestCoinDao().getSelectedData()


    // CoinPrice
    fun getAllCoinPriceData() = db.selectedCoinDao().getAllData()

    fun insertCoinPriceData(selectedCoinPriceEntity: SelectedCoinPriceEntity) =
        db.selectedCoinDao().insert(selectedCoinPriceEntity)

    fun getOneSelectedCoinData(coinName: String) = db.selectedCoinDao().getOneCoinData(coinName)
}