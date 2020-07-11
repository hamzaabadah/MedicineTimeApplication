package com.example.medicinetimeapplication.apiclasses

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class ApiController (var context: Context){

    val TAG = "tag_api_controller"
    companion object {
        @Volatile
        private var INSTANCE: ApiController? = null
        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ApiController(context).also {
                    INSTANCE = it
                }
            }
    }

    fun requester(context: Context, root: View , url:String ) {
        val requesterJsonObjectRequest = object : JsonObjectRequest(Method.GET, url, null,
            Response.Listener { res ->

            },
            Response.ErrorListener { error ->

                var message: String? = null
                when (error) {
                    is NetworkError -> {
                        message = "Cannot connect to Internet...Please check your connection!"
                    }
                    is ServerError -> {
                        message = "The server could not be found. Please try again after some time!!"
                    }
                    is AuthFailureError -> {
                        message = "Cannot connect to Internet...Please check your connection!"
                    }
                    is ParseError -> {
                        message = "Parsing error! Please try again after some time!!"
                    }
                    is NoConnectionError -> {
                        message = "Cannot connect to Internet...Please check your connection!"
                    }
                    is TimeoutError -> {
                        message = "Connection TimeOut! Please check your internet connection."
                    }
                    else -> message = error.toString()
                }

                Log.d(TAG, message)
                Toast.makeText(context!!, message, Toast.LENGTH_LONG).show()
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                return headers
            }
        }

        ApiSingleton.getInstance(context!!).addToRequestQueue(requesterJsonObjectRequest)

    }

    fun requesterPost(root: View , url:String , headers:HashMap<String,String>) {
        val requesterJsonObjectRequest = object : JsonObjectRequest(Method.POST, url, null,
            Response.Listener { res ->

            },
            Response.ErrorListener { error ->

            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                return headers
            }
        }

        ApiSingleton.getInstance(context!!).addToRequestQueue(requesterJsonObjectRequest)

    }



}