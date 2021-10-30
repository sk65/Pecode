package com.yefimoyevhen.pecode.viewmodel

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yefimoyevhen.pecode.R
import com.yefimoyevhen.pecode.util.NotificationManager
import com.yefimoyevhen.pecode.util.SharedPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class TestViewModel
@Inject constructor(
    private val notificationManager: NotificationManager,
    @ApplicationContext val context: Context,
    private val sharedPreferencesManager: SharedPreferencesManager
) : ViewModel() {


    private val _fragments: MutableLiveData<List<Int>> = MutableLiveData()
    val fragments: LiveData<List<Int>> get() = _fragments

    private val _lastPosition: MutableLiveData<Int> = MutableLiveData()
    val lastPosition: LiveData<Int> get() = _lastPosition

    init {
        _fragments.value = sharedPreferencesManager.getOrders()
        _lastPosition.value = sharedPreferencesManager.getLastPosition()
    }

    fun addFragment() {
        val templeList = _fragments.value!!.toMutableList()
        templeList.add(templeList.size + 1)
        sharedPreferencesManager.updateOrders(templeList)
        _lastPosition.value = templeList.size
        _fragments.value = templeList.toList()
    }

    fun removeFragment() {
        if (_fragments.value!!.size <= 1) {
            return
        }
        val templeList = _fragments.value!!.toMutableList()
        templeList.remove(templeList.size)
        sharedPreferencesManager.updateOrders(templeList)
        _fragments.value = templeList.toList()
    }

    fun sendNotification(order: Int) {
        val bitmap = BitmapFactory.decodeResource(
            context.applicationContext.resources,
            R.drawable.ic_plus
        )
        notificationManager.sendNotification(
            order,
            R.drawable.ic_notification,
            bitmap,
            context.getString(R.string.chat_heads_active),
            context.getString(R.string.notification) + order
        )
    }

    fun updateLastPosition(position: Int) {
        Log.i("dev", "ViewModel position: $position")
        sharedPreferencesManager.updateLastPosition(position)
        _lastPosition.value = position
    }
}