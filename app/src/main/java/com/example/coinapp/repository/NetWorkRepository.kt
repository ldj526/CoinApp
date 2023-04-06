package com.example.coinapp.repository

import com.example.coinapp.network.Api
import com.example.coinapp.network.RetrofitInstance

class NetWorkRepository {
    private val client = RetrofitInstance.getInstance().create(Api::class.java)
    suspend fun getCurrentCoinList() = client.getCurrentCoinList()
}