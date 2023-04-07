package com.example.coinapp.view.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coinapp.R
import com.example.coinapp.datamodel.UpDownDataSet

class PriceListUpDownRVAdapter(val context: Context, val dataSet: List<UpDownDataSet>) :
    RecyclerView.Adapter<PriceListUpDownRVAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val coinName = view.findViewById<TextView>(R.id.coinName)
        val coinPriceUpDown = view.findViewById<TextView>(R.id.coinPriceUpDown)
        val price = view.findViewById<TextView>(R.id.price)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PriceListUpDownRVAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.coin_price_change_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PriceListUpDownRVAdapter.ViewHolder, position: Int) {
        holder.coinName.text = dataSet[position].coinName

        if (dataSet[position].upDownPrice.contains("-")) {
            holder.coinPriceUpDown.text = "하락"
            holder.coinPriceUpDown.setTextColor(Color.parseColor("#4361ee"))
        } else {
            holder.coinPriceUpDown.text = "상승"
            holder.coinPriceUpDown.setTextColor(Color.parseColor("#d90429"))
        }

        holder.price.text = dataSet[position].upDownPrice.split(".")[0]
    }

    override fun getItemCount(): Int = dataSet.size
}