package com.example.medicinetimeapplication.apiclasses

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

object Utility {
    // Check if connected to internet
    fun isOnline(context: Context): Boolean {
        val connMgr =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    fun startNewActivity(context: Context, clazz: Class<*>) {
        val intent = Intent(context, clazz)
        context.startActivity(intent)
    }
}