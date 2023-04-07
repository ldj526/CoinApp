package com.example.coinapp.view.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coinapp.R
import com.example.coinapp.datamodel.CurrentPriceResult

class SelectRVAdapter(val context: Context, private val coinPriceList: List<CurrentPriceResult>) :
    RecyclerView.Adapter<SelectRVAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val coinName: TextView = view.findViewById(R.id.coinName)
        val coinPriceUpDown: TextView = view.findViewById(R.id.coinPriceUpDown)
        val likeImage: ImageView = view.findViewById(R.id.likeBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.intro_coin_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = coinPriceList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 코인 이름 연결
        holder.coinName.text = coinPriceList[position].coinName
        // 코인 등락 현황 연결
        val fluctate_24H = coinPriceList[position].coinInfo.fluctate_24H

        if (fluctate_24H.contains("-")) {
            holder.coinPriceUpDown.text = "하락입니다"
            holder.coinPriceUpDown.setTextColor(Color.parseColor("#4361ee"))
        } else {
            holder.coinPriceUpDown.text = "상승입니다"
            holder.coinPriceUpDown.setTextColor(Color.parseColor("#d90429"))
        }
    }
}