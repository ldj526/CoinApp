package com.example.coinapp.db.dao

import androidx.room.*
import com.example.coinapp.db.entity.InterestCoinEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InterestCoinDao {

    // Flow는 데이터의 변경 사항을 감지하기 좋다.
    @Query("SELECT * FROM interest_coin_table")
    fun getAllData(): Flow<List<InterestCoinEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(interestCoinEntity: InterestCoinEntity)

    // 사용자가 데이터를 선택했다가 다시 취소할 수도 있고, 선택안된 것을 선택할 수 있음.
    @Update
    fun update(interestCoinEntity: InterestCoinEntity)

    // 선택된 데이터만 가져옴.
    @Query("SELECT * FROM interest_coin_table WHERE selected = :selected")
    fun getSelectedData(selected: Boolean = true): List<InterestCoinEntity>

}