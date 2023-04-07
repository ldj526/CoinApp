package com.example.coinapp.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.coinapp.R
import com.example.coinapp.databinding.FragmentPriceChangeBinding

class PriceChangeFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    private var _binding: FragmentPriceChangeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPriceChangeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllSelectedCoinData()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}