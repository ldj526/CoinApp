package com.example.coinapp.network

import com.example.coinapp.network.model.CurrentPriceList
import com.example.coinapp.network.model.RecentCoinPriceList
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("public/ticker/ALL_KRW")
    suspend fun getCurrentCoinList(): CurrentPriceList

    // https://apidocs.bithumb.com/reference/%EC%B5%9C%EA%B7%BC-%EC%B2%B4%EA%B2%B0-%EB%82%B4%EC%97%AD
    // https://api.bithumb.com/public/transaction_history/BTC_KRW

    @GET("https://api.bithumb.com/public/transaction_history/{coin}_KRW")
    suspend fun getRecentCoinPrice(@Path("coin") coin: String): RecentCoinPriceList

}