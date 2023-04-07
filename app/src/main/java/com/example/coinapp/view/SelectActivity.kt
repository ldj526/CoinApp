package com.example.coinapp.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.coinapp.background.GetCoinPriceRecentTradedWorkManager
import com.example.coinapp.databinding.ActivitySelectBinding
import com.example.coinapp.view.adapter.SelectRVAdapter
import com.example.coinapp.view.main.MainActivity
import timber.log.Timber
import java.util.concurrent.TimeUnit

class SelectActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectBinding

    private val viewModel: SelectViewModel by viewModels()

    private lateinit var selectRVAdapter: SelectRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getCurrentCoinList()
        // SelectActivity 에서 데이터 관찰
        viewModel.currentPriceResult.observe(this, Observer {
            // Adapter 연결
            selectRVAdapter = SelectRVAdapter(this, it)
            binding.coinListRV.adapter = selectRVAdapter
            binding.coinListRV.layoutManager = LinearLayoutManager(this)

            Timber.d(it.toString())
        })

        binding.laterTextArea.setOnClickListener {
            viewModel.setUpFirstFlag()
            viewModel.saveSelectedCoinList(selectRVAdapter.selectedCoinList)
        }

        viewModel.save.observe(this, Observer {
            // 데이터 선택이 "done" 됐을 때 MainActivity로 넘겨준다.
            if (it.equals("done")) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

                saveInterestCoinDataPeriodic()
            }
        })
    }

    private fun saveInterestCoinDataPeriodic() {
        val myWork = PeriodicWorkRequest.Builder(
            GetCoinPriceRecentTradedWorkManager::class.java,
            15,
            TimeUnit.MINUTES
        ).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "GetCoinPriceRecentTradedWorkManager",
            ExistingPeriodicWorkPolicy.KEEP,
            myWork
        )
    }
}