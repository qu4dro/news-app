package ru.orlovvv.newsapp.workers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

abstract class NetworkBroadcastReceiver() : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        onNetworkChange()
    }

    abstract fun onNetworkChange()

}