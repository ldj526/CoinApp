package com.example.coinapp.network.model

data class CurrentPriceList(
    val status: String,
    val data: Map<String, Any>
)