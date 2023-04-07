package com.example.coinapp.network.model

import com.example.coinapp.datamodel.RecentPriceData

data class RecentCoinPriceList(
    val status: String,
    val data: List<RecentPriceData>
)