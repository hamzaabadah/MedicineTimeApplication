package com.example.medicinetimeapplication.apiclasses.authenticationpatient

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.nfc.Tag
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.example.medicinetimeapplication.MainActivity
import com.example.medicinetimeapplication.R
import com.example.medicinetimeapplication.apiclasses.ApiLinks
import com.example.medicinetimeapplication.apiclasses.ApiSingleton
import com.example.medicinetimeapplication.apiclasses.Utility
import com.example.medicinetimeapplication.apiclasses.VolleyParseError
import kotlinx.android.synthetic.main.fragment_patient_log_in.view.*
import kotlinx.android.synthetic.main.fragment_register.view.*
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException


class ApiControllerAuthPatient(var context: Context):AuthPatientInterface {

    val TAG = "tag_api_controller_auth_patient"
    companion object {
        @Volatile
        private var INSTANCE: ApiControllerAuthPatient? = null
        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ApiControllerAuthPatient(context).also {
                    INSTANCE = it
                }
            }
    }

    @SuppressLint("LongLogTag")
    override fun loginPatient(root: View) {

        var identity_num = root.id_patent_log_in_fragment_identity_num.text.toString()
        var password = root.id_patent_log_in_fragment_password.text.toString()

        var identityNumError = R.string.api_controller_auth_patient_identity_num_error

        if (identity_num.isEmpty()){
            root.id_patent_log_in_fragment_layout_identity_num.error = "enter identity number"
            root.id_patent_log_in_fragment_layout_password.error= null
        }else if (password.isEmpty()){
            root.id_patent_log_in_fragment_layout_identity_num.error = null
            root.id_patent_log_in_fragment_layout_password.error= "enter password"
        }else {
            root.id_patent_log_in_fragment_layout_identity_num.error = null
            root.id_patent_log_in_fragment_layout_password.error= null

            if (Utility.isOnline(context)){

                Log.i(TAG,"identity num is : $identity_num")
                Log.i(TAG,"pass is : $password")

                val data = JSONObject()
                data.put("identity_num",identity_num)
                data.put("password",password)

                Log.i(TAG,"data is : $data")
                Log.i(TAG,ApiLinks.LOGIN_URL)

                val progressDialog = ProgressDialog(context)
                progressDialog.setMessage("Please wait...")
                progressDialog.show()
                val requesterJsonObjectRequest = @SuppressLint("LongLogTag")
                object : JsonObjectRequest(
                    Method.POST, ApiLinks.LOGIN_URL, data,
                    Response.Listener { response ->

                        progressDialog.dismiss();
                        Log.i(TAG,response.toString() )

                        if (response.getBoolean("status")){
                            Log.i(TAG,"message server is : "+response.getString("message"))
                            Log.i(TAG,"status code server is : "+response.getString("status_code"))

                            Toast.makeText(context!!, response.getString("message")+response.getString("status_code"), Toast.LENGTH_LONG).show()
                            var array = response.getJSONObject("data")
                            var idPatient = response.getInt("id")
                            var arrayin = array.getJSONObject("token")
                            var accessToken = arrayin.getString("access_token")
                            var tokenType = arrayin.getString("token_type")

                            val sharedPreferences= context.getSharedPreferences("isLogin", Context.MODE_PRIVATE)
                            val editorLogin= sharedPreferences.edit()
                            editorLogin.putString("token", "$tokenType $accessToken")
                            editorLogin.putInt("id_patient",idPatient)
                            editorLogin.apply()

                            Utility.startNewActivity(context,MainActivity::class.java)


                        }else {
                            Log.i(TAG,"message server is : "+response.getString("message"))
                            var jsonObject = response.getJSONObject("errors")
                            var password = jsonObject.getJSONArray("password")
                        }

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

                        parseVolleyError(error)
                        progressDialog.dismiss()
                        Log.i(TAG,error.networkResponse.data.toString())
                        Toast.makeText(context!!, error.message.toString(), Toast.LENGTH_LONG).show()
                    }
                ) {
                    override fun getHeaders(): HashMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Accept"] = "application/json"
                        return headers
                    }
                }

                ApiSingleton.getInstance(context!!).addToRequestQueue(requesterJsonObjectRequest)

            }else {
                Toast.makeText(context, R.string.no_connection, Toast.LENGTH_LONG).show();
            }
        }

    }

    @SuppressLint("LongLogTag")
    fun parseVolleyError(error: VolleyError) {
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

            Log.i(TAG,"massage error $massage")
            Toast.makeText(context,massage,Toast.LENGTH_SHORT).show()
        } catch (e: JSONException) {
        } catch (errorr: UnsupportedEncodingException) {
        }
    }

    @SuppressLint("LongLogTag")
    override fun registerPatient(root: View) {
        var name = root.patent_register_fragment_id_name.text.toString()
        var identityNum = root.patent_register_fragment_id_identity_num.text.toString()
        var gender = root.patent_register_fragment_id_gender.text.toString()
        var address = root.patent_register_fragment_id_address.text.toString()
        var mobile = root.patent_register_fragment_id_mobile.text.toString()
        var birthdate = root.patent_register_fragment_id_birth_date.text.toString()
        var username = root.patent_register_fragment_id_username.text.toString()
        var password = root.patent_register_fragment_id_password.text.toString()

        if (name.isEmpty()){
            root.patent_register_fragment_id_layout_name.error="enter name"
            root.patent_register_fragment_id_layout_identity_num.error=null
            root.patent_register_fragment_id_layout_address.error=null
            root.patent_register_fragment_id_layout_mobile.error=null
            root.patent_register_fragment_id_layout_username.error=null
            root.patent_register_fragment_id_layout_password.error=null
        }else if (username.isEmpty()){
            root.patent_register_fragment_id_layout_name.error=null
            root.patent_register_fragment_id_layout_identity_num.error=null
            root.patent_register_fragment_id_layout_address.error=null
            root.patent_register_fragment_id_layout_mobile.error=null
            root.patent_register_fragment_id_layout_username.error="enter username"
            root.patent_register_fragment_id_layout_password.error=null
        }else if (address.isEmpty()){
            root.patent_register_fragment_id_layout_name.error=null
            root.patent_register_fragment_id_layout_identity_num.error=null
            root.patent_register_fragment_id_layout_address.error="enter address"
            root.patent_register_fragment_id_layout_mobile.error=null
            root.patent_register_fragment_id_layout_username.error=null
            root.patent_register_fragment_id_layout_password.error=null
        }else if (mobile.isEmpty()){
            root.patent_register_fragment_id_layout_name.error=null
            root.patent_register_fragment_id_layout_identity_num.error=null
            root.patent_register_fragment_id_layout_address.error=null
            root.patent_register_fragment_id_layout_mobile.error="enter mobile"
            root.patent_register_fragment_id_layout_username.error=null
            root.patent_register_fragment_id_layout_password.error=null
        }else if (identityNum.isEmpty()){
            root.patent_register_fragment_id_layout_name.error=null
            root.patent_register_fragment_id_layout_identity_num.error="enter identity number"
            root.patent_register_fragment_id_layout_address.error=null
            root.patent_register_fragment_id_layout_mobile.error=null
            root.patent_register_fragment_id_layout_username.error=null
            root.patent_register_fragment_id_layout_password.error=null
        }else if (password.isEmpty()){
            root.patent_register_fragment_id_layout_name.error=null
            root.patent_register_fragment_id_layout_identity_num.error=null
            root.patent_register_fragment_id_layout_address.error=null
            root.patent_register_fragment_id_layout_mobile.error=null
            root.patent_register_fragment_id_layout_username.error=null
            root.patent_register_fragment_id_layout_password.error="enter password"
        }else if (birthdate.isEmpty()){
            Toast.makeText(context,R.string.patent_register_fragment_birth_date,Toast.LENGTH_SHORT).show()
            root.patent_register_fragment_id_layout_name.error=null
            root.patent_register_fragment_id_layout_identity_num.error=null
            root.patent_register_fragment_id_layout_address.error=null
            root.patent_register_fragment_id_layout_address.error=null
            root.patent_register_fragment_id_layout_mobile.error=null
            root.patent_register_fragment_id_layout_username.error=null
            root.patent_register_fragment_id_layout_password.error=null
        }else if (gender.isEmpty()){
            Toast.makeText(context,R.string.patent_register_fragment_title_gender,Toast.LENGTH_SHORT).show()
            root.patent_register_fragment_id_layout_name.error=null
            root.patent_register_fragment_id_layout_identity_num.error=null
            root.patent_register_fragment_id_layout_address.error=null
            root.patent_register_fragment_id_layout_address.error=null
            root.patent_register_fragment_id_layout_mobile.error=null
            root.patent_register_fragment_id_layout_username.error=null
            root.patent_register_fragment_id_layout_password.error=null
        }else {
            root.patent_register_fragment_id_layout_name.error=null
            root.patent_register_fragment_id_layout_identity_num.error=null
            root.patent_register_fragment_id_layout_address.error=null
            root.patent_register_fragment_id_layout_address.error=null
            root.patent_register_fragment_id_layout_mobile.error=null
            root.patent_register_fragment_id_layout_username.error=null
            root.patent_register_fragment_id_layout_password.error=null

            if (Utility.isOnline(context)){
                Log.i(TAG,"identity num is : $name")
                Log.i(TAG,"identityNum is : $identityNum")
                Log.i(TAG,"gender is : $gender")
                Log.i(TAG,"address is : $address")
                Log.i(TAG,"mobile is : $mobile")
                Log.i(TAG,"birthdate is : $birthdate")
                Log.i(TAG,"username is : $username")
                Log.i(TAG,"password is : $password")

                val data = JSONObject()
                data.put("name",name)
                data.put("identity_num",identityNum)
                data.put("gender",gender)
                data.put("address",address)
                data.put("mobile",mobile)
                data.put("birthdate",birthdate)
                data.put("username",username)
                data.put("password",password)

                Log.i(TAG,"data is : $data")
                Log.i(TAG,"Login link is : ${ApiLinks.REGISTER_URL}")

                val progressDialog = ProgressDialog(context)
                progressDialog.setMessage("Please wait...")
                progressDialog.show()

                val requesterJsonObjectRequest = object : JsonObjectRequest(Method.POST, ApiLinks.REGISTER_URL, data,
                    Response.Listener { response ->

                        if (response.getBoolean("status")){
                            Log.i(TAG,"message server is : "+response.getString("message"))
                            Log.i(TAG,"status code server is : "+response.getString("status_code"))
                        }else{
                            Log.i(TAG,"message server is : "+response.getString("message"))
                            var jsonObject = response.getJSONObject("errors")
                            var password = jsonObject.getJSONArray("password")
                        }
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

                        VolleyParseError.getInstance(context).parseVolleyError(error,TAG)
                        Log.d(TAG, message)
                        Toast.makeText(context!!, message, Toast.LENGTH_LONG).show()
                    }
                ) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Accept"] = "application/json"
                        return headers
                    }
                }

                ApiSingleton.getInstance(context!!).addToRequestQueue(requesterJsonObjectRequest)

            }
            else{
                Toast.makeText(context,R.string.no_connection,Toast.LENGTH_SHORT).show()
            }
        }

    }




}