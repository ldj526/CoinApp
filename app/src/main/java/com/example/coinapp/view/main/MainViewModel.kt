package com.example.coinapp.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.coinapp.db.entity.InterestCoinEntity
import com.example.coinapp.repository.DBRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val dbRepository = DBRepository()

    lateinit var selectedCoinList: LiveData<List<InterestCoinEntity>>

    // CoinListFragment
    fun getAllInterestCoinData() = viewModelScope.launch {
        val coinList = dbRepository.getAllInterestCoinData().asLiveData()
        selectedCoinList = coinList
    }

    fun updateInterestCoinData(interestCoinEntity: InterestCoinEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            // 선택된 것은 선택되지 않은 것으로, 선택되지 않은 것은 선택된 것으로 바꿔준다.
            interestCoinEntity.selected = !interestCoinEntity.selected

            // db에 업데이트
            dbRepository.updateInterestCoinData(interestCoinEntity)
        }
}