package com.yefimoyevhen.pecode.util

import android.content.SharedPreferences
import javax.inject.Inject


class SharedPreferencesManager
@Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    companion object {
        const val PREF_KEY = "appSetting"
        private const val ORDERS_KEY = "ORDERS_KEY"
        private const val LAST_POSITION_KEY = "POSITION_KEY"
    }

    fun updateLastPosition(position: Int) =
        sharedPreferences.edit().putInt(LAST_POSITION_KEY, position).apply()

    fun getLastPosition() =
        sharedPreferences.getInt(LAST_POSITION_KEY,1)

    fun updateOrders(orders: List<Int>) {
        val temple = orders.map { order -> order.toString() }.toSet()
        sharedPreferences.edit().putStringSet(ORDERS_KEY, temple).apply()
    }

    fun getOrders() =
        sharedPreferences.getStringSet(ORDERS_KEY, setOf("1"))!!.toList()
            .map { order -> order.toInt() }.sorted()

}