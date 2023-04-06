package com.example.coinapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.coinapp.R

class SelectActivity : AppCompatActivity() {

    private val viewModel: SelectViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        viewModel.getCurrentCoinList()
    }
}