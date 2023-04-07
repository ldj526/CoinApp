package com.example.coinapp.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinapp.datamodel.CurrentPrice
import com.example.coinapp.datamodel.CurrentPriceResult
import com.example.coinapp.network.datastore.MyDataStore
import com.example.coinapp.db.entity.InterestCoinEntity
import com.example.coinapp.repository.DBRepository
import com.example.coinapp.repository.NetWorkRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class SelectViewModel : ViewModel() {

    private val netWorkRepository = NetWorkRepository()
    private val dbRepository = DBRepository()

    private lateinit var currentPriceResultList: ArrayList<CurrentPriceResult>

    // 데이터 변화를 관찰하는 LiveData
    private val _currentPriceResult = MutableLiveData<List<CurrentPriceResult>>()
    val currentPriceResult: LiveData<List<CurrentPriceResult>>
        get() = _currentPriceResult

    private val _saved = MutableLiveData<String>()
    val save: LiveData<String>
        get() = _saved

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
        // 전체 코인 데이터
        _currentPriceResult.value = currentPriceResultList
    }

    fun setUpFirstFlag() = viewModelScope.launch {
        MyDataStore().setupFirstData()
    }

    // https://developer.android.com/kotlin/coroutines/coroutines-adv?hl=ko (Dispatchers.IO 설명)
    fun saveSelectedCoinList(selectedCoinList: ArrayList<String>) =
        viewModelScope.launch(Dispatchers.IO) {
            // 코인 데이터 가져오기
            for (coin in currentPriceResultList) {
                Timber.d(coin.toString())

                // 내가 선택한 코인을 포함하고 있으면 true, 아니면 false
                val selected = selectedCoinList.contains(coin.coinName)

                val interestCoinEntity = InterestCoinEntity(
                    0,
                    coin.coinName,
                    coin.coinInfo.opening_price,
                    coin.coinInfo.closing_price,
                    coin.coinInfo.min_price,
                    coin.coinInfo.max_price,
                    coin.coinInfo.units_traded,
                    coin.coinInfo.acc_trade_value,
                    coin.coinInfo.prev_closing_price,
                    coin.coinInfo.units_traded_24H,
                    coin.coinInfo.acc_trade_value_24H,
                    coin.coinInfo.fluctate_24H,
                    coin.coinInfo.fluctate_rate_24H,
                    selected
                )

                // db에 저장
                interestCoinEntity.let {
                    dbRepository.insertInterestCoinData(it)
                }
            }
            // Main쓰레드로 전환, withContext 가 끝날 때까지 Coroutine은 정지,
            withContext(Dispatchers.Main) {
                _saved.value = "done"
            }
        }
}