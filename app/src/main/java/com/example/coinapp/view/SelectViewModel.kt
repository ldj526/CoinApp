package com.example.coinapp.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinapp.datamodel.CurrentPrice
import com.example.coinapp.datamodel.CurrentPriceResult
import com.example.coinapp.repository.NetWorkRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import timber.log.Timber

class SelectViewModel : ViewModel() {

    private val netWorkRepository = NetWorkRepository()

    private lateinit var currentPriceResultList: ArrayList<CurrentPriceResult>

    // 데이터 변화를 관찰하는 LiveData
    private val _currentPriceResult = MutableLiveData<List<CurrentPriceResult>>()
    val currentPriceResult: LiveData<List<CurrentPriceResult>>
        get() = _currentPriceResult

    // Coroutine
    fun getCurrentCoinList() = viewModelScope.launch {
        val result = netWorkRepository.getCurrentCoinList()

        currentPriceResultList = ArrayList()

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
        _currentPriceResult.value = currentPriceResultList
    }
}