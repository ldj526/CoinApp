package com.example.coinapp.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.coinapp.App

class MyDataStore {
    private val context = App.context()

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_pref")
    }

    private val mDataStore: DataStore<Preferences> = context.dataStore

    private val FIRST_FLAG = booleanPreferencesKey("FIRST_FLAG")

    // 메인 액티비티로 갈 때 처음이면 true로 변경
    suspend fun setupFirstData() {
        mDataStore.edit { preferences ->
            preferences[FIRST_FLAG] = true
        }
    }

    suspend fun getFirstData(): Boolean {
        var currentValue = false

        mDataStore.edit { preferences ->
            currentValue = preferences[FIRST_FLAG] ?: false
        }

        return currentValue
    }
}