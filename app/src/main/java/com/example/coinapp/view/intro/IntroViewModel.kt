package com.example.coinapp.view.intro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinapp.datastore.MyDataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class IntroViewModel : ViewModel() {

    // LiveData 설정
    private val _first = MutableLiveData<Boolean>()
    val first: LiveData<Boolean>
        get() = _first

    fun checkFirstFlag() = viewModelScope.launch {
        // animation delay
        delay(2000)

        val getData = MyDataStore().getFirstData()
        _first.value = getData
        Timber.d(getData.toString())
    }
}