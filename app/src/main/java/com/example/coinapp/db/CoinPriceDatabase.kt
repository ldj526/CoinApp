package com.example.coinapp.db

import android.content.Context
import androidx.room.*
import com.example.coinapp.db.dao.InterestCoinDao
import com.example.coinapp.db.dao.SelectedCoinPriceDao
import com.example.coinapp.db.entity.DateConverters
import com.example.coinapp.db.entity.InterestCoinEntity
import com.example.coinapp.db.entity.SelectedCoinPriceEntity

@Database(entities = [InterestCoinEntity::class, SelectedCoinPriceEntity::class], version = 3)
@TypeConverters(DateConverters::class)
abstract class CoinPriceDatabase : RoomDatabase() {

    abstract fun interestCoinDao(): InterestCoinDao
    abstract fun selectedCoinDao(): SelectedCoinPriceDao

    companion object {
        @Volatile
        private var INSTANCE: CoinPriceDatabase? = null

        fun getDatabase(context: Context): CoinPriceDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CoinPriceDatabase::class.java,
                    "coin_database"
                )
                    .fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}