package com.example.coinapp.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.*

@Entity(tableName = "selected_coin_price_table")
data class SelectedCoinPriceEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val coinName: String,
    val transaction_date: String,
    val type: String,
    val units_traded: String,
    val price: String,
    val total: String,
    val timeStamp: Date
)

// timeStamp의 Type은 DB에서 에러가 나기 때문에 Converter를 만들어 준다.
class DateConverters {
    @TypeConverter
    fun fromTimestamp(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return date.time
    }
}