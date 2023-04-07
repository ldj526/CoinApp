package com.example.coinapp.view.intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import com.example.coinapp.MainActivity
import com.example.coinapp.R
import com.example.coinapp.databinding.ActivityIntroBinding
import timber.log.Timber

class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding

    private val viewModel: IntroViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()

        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.d("onCreate")

        viewModel.checkFirstFlag()
        viewModel.first.observe(this, Observer {
            if (it) {
                // 처음 접속이 아닌 사용자는 MainActivity로 이동
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                // 처음 접속인 사용자
                binding.fragmentContainerView.visibility = View.VISIBLE
            }
        })

    }
}