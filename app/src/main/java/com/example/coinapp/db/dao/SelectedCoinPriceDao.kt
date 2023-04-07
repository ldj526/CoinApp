package com.example.coinapp.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.coinapp.db.entity.SelectedCoinPriceEntity

@Dao
interface SelectedCoinPriceDao {

    // getAllData
    @Query("SELECT * FROM selected_coin_price_table")
    fun getAllData(): List<SelectedCoinPriceEntity>

    // insert
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(selectedCoinPriceEntity: SelectedCoinPriceEntity)

    // 하나의 코인에 대해 저장된 정보를 가져오는 기능
    @Query("SELECT * FROM selected_coin_price_table WHERE coinName = :coinName")
    fun getOneCoinData(coinName: String): List<SelectedCoinPriceEntity>
}