package com.example.coinapp.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coinapp.R
import com.example.coinapp.db.entity.InterestCoinEntity

class CoinListRVAdapter(val context: Context, val dataSet: List<InterestCoinEntity>) :
    RecyclerView.Adapter<CoinListRVAdapter.ViewHolder>() {

    // ItemClick interface 생성
    interface ItemClick {
        fun onClick(view: View, position: Int)
    }
    var itemClick: ItemClick? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val coinName = view.findViewById<TextView>(R.id.coinName)
        val likeBtn = view.findViewById<ImageView>(R.id.likeBtn)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CoinListRVAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.main_coin_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinListRVAdapter.ViewHolder, position: Int) {

        // 이미지 클릭 이벤트
        holder.itemView.findViewById<ImageView>(R.id.likeBtn).setOnClickListener { v ->
            itemClick?.onClick(v, position)
        }

        holder.coinName.text = dataSet[position].coin_name

        // 선택된 코인 리스트들의 이미지는 빨간색, 그렇지 않은 이미지는 회색으로 설정
        val selected = dataSet[position].selected
        if (selected) {
            holder.likeBtn.setImageResource(R.drawable.like_red)
        } else {
            holder.likeBtn.setImageResource(R.drawable.like_grey)
        }
    }

    override fun getItemCount(): Int = dataSet.size
}