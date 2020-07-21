package com.example.medicinetimeapplication.apiclasses

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.VolleyError
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException

class VolleyParseError (var context: Context){
    companion object {
        @Volatile
        private var INSTANCE: VolleyParseError? = null
        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: VolleyParseError(context).also {
                    INSTANCE = it
                }
            }
    }

    fun parseVolleyError(error: VolleyError, tag:String) {
        try {

            val responseBody = String(error.networkResponse.data)
            val data = JSONObject(responseBody)
            var massage = data.getString("message")

            if (error.networkResponse.statusCode == 422) {
                var errors = data.getJSONObject("errors")
//                for (i in 0 until errors.length()){
//                    errors.keys()
//                }
                val iter: Iterator<String> = errors.keys()
                while (iter.hasNext()) {
                    val key = iter.next()
                    try {
                        val value = errors.getJSONArray(key)
                        massage = value.get(0) as String
                        break
                    } catch (e: JSONException) {
                        // Something went wrong!
                    }
                }
            }


            Log.i(tag,"statusCode is :" + error.networkResponse.statusCode)
            Log.i(tag,"massage error is :  $massage")
            Toast.makeText(context,massage, Toast.LENGTH_SHORT).show()
        } catch (e: JSONException) {
        } catch (errorr: UnsupportedEncodingException) {
        }
    }

    private fun getKeys(errors:JSONObject):String{
        var massage=""
        val iter: Iterator<String> = errors.keys()
        while (iter.hasNext()) {
            val key = iter.next()
            try {
                val value = errors.getJSONArray(key)
                massage =  value.get(0) as String
                break
            } catch (e: JSONException) {
                // Something went wrong!
            }
        }
        return massage

    }
}