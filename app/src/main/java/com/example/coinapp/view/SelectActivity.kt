package com.example.coinapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.coinapp.R
import timber.log.Timber

class SelectActivity : AppCompatActivity() {

    private val viewModel: SelectViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        viewModel.getCurrentCoinList()
        // SelectActivity 에서 데이터 관찰
        viewModel.currentPriceResult.observe(this, Observer {
            Timber.d(it.toString())
        })
    }
}