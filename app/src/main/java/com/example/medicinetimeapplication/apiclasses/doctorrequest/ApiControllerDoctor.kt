@file:Suppress("DEPRECATION")

package com.example.medicinetimeapplication.apiclasses.doctorrequest

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.example.medicinetimeapplication.R
import com.example.medicinetimeapplication.adapter.MyDoctorsAdapter
import com.example.medicinetimeapplication.adapter.NextVisitAdapter
import com.example.medicinetimeapplication.apiclasses.ApiLinks
import com.example.medicinetimeapplication.apiclasses.ApiSingleton
import com.example.medicinetimeapplication.apiclasses.Utility
import com.example.medicinetimeapplication.apiclasses.VolleyParseError
import com.example.medicinetimeapplication.apiclasses.patientrequests.PatientRequestInterface
import com.example.medicinetimeapplication.model.*
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class ApiControllerDoctor (var context: Context) : DoctorRequestInterface {
    val TAG = "tag_api_controller_doctor"
    companion object {
        @Volatile
        private var INSTANCE: ApiControllerDoctor? = null
        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ApiControllerDoctor(context).also {
                    INSTANCE = it
                }
            }
    }

    override fun getAuthenticatedPatientDoctors(root: View) {
        if (Utility.isOnline(context)){

            val progressDialog = ProgressDialog(context)
            progressDialog.setMessage("Please wait...")
            progressDialog.show()
            val requesterJsonObjectRequest = @SuppressLint("LongLogTag")
            object : JsonObjectRequest(
                Method.GET, ApiLinks.MY_DOCTORS, null,
                Response.Listener { response ->
                    progressDialog.dismiss();
                    if (response.getBoolean("status")){
                        Log.i(TAG,response.getString("message"))
                        Log.i(TAG, response.getInt("status_code").toString())
                        Log.i(TAG,ApiLinks.MY_DOCTORS)

                        var myDoctorsArrayList = ArrayList<MyDoctorModel>()

                        var data = response.getJSONArray("data")
                        for (i in 0 until data.length()){
                            var doctorInfo = data.getJSONObject(i)
                            var doctorId = doctorInfo.getInt("id")
                            var doctorName = doctorInfo.getString("name")
                            var clinicInfo = doctorInfo.getJSONObject("clinic")
                            var clinicId=clinicInfo.getInt("id")
                            var clinicName = clinicInfo.getString("name")
                            var myDoctorModel = MyDoctorModel(doctorId,doctorName,clinicId,clinicName)
                            Log.i(TAG,myDoctorModel.toString())

                            myDoctorsArrayList.add(myDoctorModel)
                        }

                        Log.i(TAG,myDoctorsArrayList.size.toString())
                        val myDoctorsAdapter = MyDoctorsAdapter(context, myDoctorsArrayList,root)
                        root.dashboard_fragment_id_rc_my_doctors.adapter = myDoctorsAdapter
                        root.dashboard_fragment_id_rc_my_doctors.layoutManager = LinearLayoutManager(context)
                        root.dashboard_fragment_id_rc_my_doctors.setHasFixedSize(true)

                    }else{
                        Log.i(TAG,"message if status not true : "+response.getString("message"))
                        Log.i(TAG, "status_code if status not true : "+response.getInt("status_code").toString())
                    }

                },
                Response.ErrorListener { error ->
                    VolleyParseError.getInstance(context).parseVolleyError(error,TAG)
                }
            ) {
                override fun getHeaders(): MutableMap<String, String> {

                    val preferences = context.getSharedPreferences("isLogin", AppCompatActivity.MODE_PRIVATE)
                    var loggedIn= preferences.getString("token","")
                    Log.i(TAG, "Token user is : $loggedIn")
                    val headers = HashMap<String, String>()
                    headers["Accept"] = "application/json"
                    headers["Authorization"] = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiNjE5NGYyMmIwODBiNDIxY2QwYWZmYWQyNGNhNmRiNDQ3ZDUzZDg3MWE1ZWI0MzdiYzZmZjViZWFhYmVmNzcyZTUyNmM4MTJmNWY0ZDQ4MTciLCJpYXQiOjE1OTQ4MzA2ODgsIm5iZiI6MTU5NDgzMDY4OCwiZXhwIjozNDg4Mjg2Njg4LCJzdWIiOiIxMCIsInNjb3BlcyI6W119.avbkRplLU-Qk4PEaxzvoTRFnE_VIcZdCDHemy_d1kqTOtupAdF_QWm_hT_XXWjLQPuCYW7-ywZ_HKW9Xfo8RQMPblVh_h5jJ10oSTOCu2i5xP8McXl65SkqiVBn0yIZxMUxrbR1bJvzwd7FUSc8eBNbyrKUmV_MaTjrXy_nMQxRBoPL0AxfDkl0BptcBknyVGkD_nZtx3LsHyuSKlr1TAT95kCRBPPZA8XlRb7VmIdnUFyPQ73F0vtGOmERCkJLpitxKwUZ8EwkcksAT7A6NBNUdisaSkdvIeCJbReco0YE4kuq4RFJa6gxIzjpl-Thg0xlMerp675ms51nr-4SkVS7fgYIaCv-Kte3loVNoIkEf0GjaOKvnrKX-ZlSGd3uFC6nyn46G5gzQdph3_MqfxvaTy47GmGgF6_qUdKe-wT31KvbjZ4fEarSpkFkQHVmtPFSk2r8S1UUhCYmVDqWcszTBb_H4qerKpOExXRcqmXa4BBSbUVVBRocKUIbU5ldnTkHV51cAzup0NIaCQ1acfY_4kzBippOxaMlpS26wpNx_SgLXed5T1f5Bfu3YqEEinHxCRIgsSVZWpH_m_qgNy-2pKdcNUegCUOYDfB-oOc06NTxPha3Ax0IzaOEBwbzEn_LG7-BihveItsrk7xpTmtUF09PbgyWVy0qEWH3MMVo"
                    return headers
                }
            }

            ApiSingleton.getInstance(context!!).addToRequestQueue(requesterJsonObjectRequest)


        }else
            Toast.makeText(context, R.string.no_connection, Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("LongLogTag")
    override fun getAuthenticatedPatientDoctorDetails(root: View, idDoctor:Int) {
        if(Utility.isOnline(context)){

            val preferences = context.getSharedPreferences("isLogin", AppCompatActivity.MODE_PRIVATE)
            var idPatientShared= preferences.getInt("id_patient",0)
            Log.i(TAG, "Id Patient Shared user is : $idPatientShared")

            val progressDialog = ProgressDialog(context)
            progressDialog.setMessage("Please wait...")
            progressDialog.show()
            Log.d(TAG, "${ApiLinks.DOCTOR_INFO}$idDoctor")

            val requesterJsonObjectRequest = @SuppressLint("LongLogTag")
            object : JsonObjectRequest(Method.GET, "${ApiLinks.DOCTOR_INFO}$idDoctor", null,
                Response.Listener { response ->

                    if (response.getBoolean("status")){
                        Log.i(TAG,response.getString("message"))
                        Log.i(TAG, response.getInt("status_code").toString())
                        Log.i(TAG,"${ApiLinks.DOCTOR_INFO}$idDoctor")

                        var data = response.getJSONObject("data")
                        var patientsArray= response.getJSONArray("patients")
                        for (i in 0 until patientsArray.length()){
                            var patientJsonObject = patientsArray.getJSONObject(i)
                            var patientId= patientJsonObject.getInt("id")
                            if (patientId!=null && patientId == idPatientShared){
                                var patientDrugs = patientJsonObject.getJSONArray("drugs")
                                for (i in 0 until patientDrugs.length()){
                                    var drugsJsonObject = patientDrugs.getJSONObject(i)

                                }

                            }else{

                            }
                        }
                    }else{
                        Log.i(TAG,"message if status not true : "+response.getString("message"))
                        Log.i(TAG, "status_code if status not true : "+response.getInt("status_code").toString())
                    }
                },
                Response.ErrorListener { error ->
                    VolleyParseError.getInstance(context).parseVolleyError(error,TAG)
                }
            ) {
                override fun getHeaders(): MutableMap<String, String> {
                    val preferences = context.getSharedPreferences("isLogin", AppCompatActivity.MODE_PRIVATE)
                    var loggedIn= preferences.getString("token","")
                    Log.i(TAG, "Token user is : $loggedIn")
                    val headers = HashMap<String, String>()
                    headers["Accept"] = "application/json"
                    headers["Authorization"]=loggedIn.toString()
                    //headers["Authorization"] = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiNjE5NGYyMmIwODBiNDIxY2QwYWZmYWQyNGNhNmRiNDQ3ZDUzZDg3MWE1ZWI0MzdiYzZmZjViZWFhYmVmNzcyZTUyNmM4MTJmNWY0ZDQ4MTciLCJpYXQiOjE1OTQ4MzA2ODgsIm5iZiI6MTU5NDgzMDY4OCwiZXhwIjozNDg4Mjg2Njg4LCJzdWIiOiIxMCIsInNjb3BlcyI6W119.avbkRplLU-Qk4PEaxzvoTRFnE_VIcZdCDHemy_d1kqTOtupAdF_QWm_hT_XXWjLQPuCYW7-ywZ_HKW9Xfo8RQMPblVh_h5jJ10oSTOCu2i5xP8McXl65SkqiVBn0yIZxMUxrbR1bJvzwd7FUSc8eBNbyrKUmV_MaTjrXy_nMQxRBoPL0AxfDkl0BptcBknyVGkD_nZtx3LsHyuSKlr1TAT95kCRBPPZA8XlRb7VmIdnUFyPQ73F0vtGOmERCkJLpitxKwUZ8EwkcksAT7A6NBNUdisaSkdvIeCJbReco0YE4kuq4RFJa6gxIzjpl-Thg0xlMerp675ms51nr-4SkVS7fgYIaCv-Kte3loVNoIkEf0GjaOKvnrKX-ZlSGd3uFC6nyn46G5gzQdph3_MqfxvaTy47GmGgF6_qUdKe-wT31KvbjZ4fEarSpkFkQHVmtPFSk2r8S1UUhCYmVDqWcszTBb_H4qerKpOExXRcqmXa4BBSbUVVBRocKUIbU5ldnTkHV51cAzup0NIaCQ1acfY_4kzBippOxaMlpS26wpNx_SgLXed5T1f5Bfu3YqEEinHxCRIgsSVZWpH_m_qgNy-2pKdcNUegCUOYDfB-oOc06NTxPha3Ax0IzaOEBwbzEn_LG7-BihveItsrk7xpTmtUF09PbgyWVy0qEWH3MMVo"
                    return headers
                }
            }

            ApiSingleton.getInstance(context!!).addToRequestQueue(requesterJsonObjectRequest)

        }else{
            Toast.makeText(context, R.string.no_connection, Toast.LENGTH_SHORT).show()
        }
    }
}