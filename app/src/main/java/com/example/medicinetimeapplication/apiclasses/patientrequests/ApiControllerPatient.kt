package com.example.medicinetimeapplication.apiclasses.patientrequests

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
import com.example.medicinetimeapplication.adapter.NextVisitAdapter
import com.example.medicinetimeapplication.apiclasses.ApiLinks
import com.example.medicinetimeapplication.apiclasses.ApiSingleton
import com.example.medicinetimeapplication.apiclasses.Utility
import com.example.medicinetimeapplication.apiclasses.VolleyParseError
import com.example.medicinetimeapplication.helperclasses.SharedPreferencesHelper
import com.example.medicinetimeapplication.model.Clinic
import com.example.medicinetimeapplication.model.Doctor
import com.example.medicinetimeapplication.model.NextVisitModel
import com.example.medicinetimeapplication.model.PatientVisit
import kotlinx.android.synthetic.main.fragment_home.view.*

@Suppress("DEPRECATION")
class ApiControllerPatient (var context: Context) : PatientRequestInterface {
    val TAG = "tag_api_controller_patient"
    companion object {
        @Volatile
        private var INSTANCE: ApiControllerPatient? = null
        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ApiControllerPatient(context).also {
                    INSTANCE = it
                }
            }
    }

    override fun getAuthenticatedPatientInfo(root: View) {
        if (Utility.isOnline(context)){

            val progressDialog = ProgressDialog(context)
            progressDialog.setMessage("Please wait...")
            progressDialog.show()

            val requesterJsonObjectRequest = @SuppressLint("LongLogTag")
            object : JsonObjectRequest(
                Method.GET, ApiLinks.PATIENT_INFO_URL, null,
                Response.Listener { response ->


                    if (response.getBoolean("status")){
                        Log.i(TAG,response.getString("message"))
                        Log.i(TAG, response.getInt("status_code").toString())

                        var arrayListNextVisitModel = ArrayList<NextVisitModel>()



                        var data = response.getJSONObject("data")

                        var doctorsArray= data.getJSONArray("doctors")
                        for (i in 0 until doctorsArray.length()){
                            var doctorJsonObject = doctorsArray.getJSONObject(i)
                            var doctorId = doctorJsonObject.getInt("id")
                            var doctorClinicId= doctorJsonObject.getInt("clinic_id")
                            var doctorName = doctorJsonObject.getString("name")
                            var doctor= Doctor(doctorId,doctorClinicId,doctorName)
                            Log.i(TAG, doctorId.toString())
                            Log.i(TAG, doctorName)

                            var patientVisitJsonObject = doctorJsonObject.getJSONObject("patient_visit")
                            var patientId= patientVisitJsonObject.getInt("patient_id")
                            var doctorIdVisited = patientVisitJsonObject.getInt("doctor_id")
                            var visitAt = patientVisitJsonObject.getString("visit_at")
                            var patientVisit = PatientVisit(patientId,doctorIdVisited,visitAt)
                            Log.i(TAG, visitAt)

                            var clinicJsonObject = doctorJsonObject.getJSONObject("clinic")
                            var clinicId = clinicJsonObject.getInt("id")
                            var clinicName = clinicJsonObject.getString("name")
                            var clinicAddress = clinicJsonObject.getString("address")
                            var clinicPhone = clinicJsonObject.getString("phone")
                            var clinic= Clinic(clinicId,clinicName,clinicAddress,clinicPhone)
                            Log.i(TAG, "$clinicId , $clinicName")

                            var nextVisitModel= NextVisitModel(doctor,patientVisit,clinic)
                            arrayListNextVisitModel.add(nextVisitModel)
                        }


                        val nextVisitAdapter = NextVisitAdapter(context, arrayListNextVisitModel,root)
                        root.home_fragment_id_rc_next_visits.adapter = nextVisitAdapter

                        root.home_fragment_id_rc_next_visits.layoutManager = LinearLayoutManager(context,
                            LinearLayoutManager.HORIZONTAL,false)
                        root.home_fragment_id_rc_next_visits.setHasFixedSize(true)
                        progressDialog.dismiss();

                    }else{
                        progressDialog.dismiss();
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
                    headers["Authorization"]= loggedIn.toString()
                    //headers["Authorization"] = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiNjE5NGYyMmIwODBiNDIxY2QwYWZmYWQyNGNhNmRiNDQ3ZDUzZDg3MWE1ZWI0MzdiYzZmZjViZWFhYmVmNzcyZTUyNmM4MTJmNWY0ZDQ4MTciLCJpYXQiOjE1OTQ4MzA2ODgsIm5iZiI6MTU5NDgzMDY4OCwiZXhwIjozNDg4Mjg2Njg4LCJzdWIiOiIxMCIsInNjb3BlcyI6W119.avbkRplLU-Qk4PEaxzvoTRFnE_VIcZdCDHemy_d1kqTOtupAdF_QWm_hT_XXWjLQPuCYW7-ywZ_HKW9Xfo8RQMPblVh_h5jJ10oSTOCu2i5xP8McXl65SkqiVBn0yIZxMUxrbR1bJvzwd7FUSc8eBNbyrKUmV_MaTjrXy_nMQxRBoPL0AxfDkl0BptcBknyVGkD_nZtx3LsHyuSKlr1TAT95kCRBPPZA8XlRb7VmIdnUFyPQ73F0vtGOmERCkJLpitxKwUZ8EwkcksAT7A6NBNUdisaSkdvIeCJbReco0YE4kuq4RFJa6gxIzjpl-Thg0xlMerp675ms51nr-4SkVS7fgYIaCv-Kte3loVNoIkEf0GjaOKvnrKX-ZlSGd3uFC6nyn46G5gzQdph3_MqfxvaTy47GmGgF6_qUdKe-wT31KvbjZ4fEarSpkFkQHVmtPFSk2r8S1UUhCYmVDqWcszTBb_H4qerKpOExXRcqmXa4BBSbUVVBRocKUIbU5ldnTkHV51cAzup0NIaCQ1acfY_4kzBippOxaMlpS26wpNx_SgLXed5T1f5Bfu3YqEEinHxCRIgsSVZWpH_m_qgNy-2pKdcNUegCUOYDfB-oOc06NTxPha3Ax0IzaOEBwbzEn_LG7-BihveItsrk7xpTmtUF09PbgyWVy0qEWH3MMVo"
                    return headers
                }
            }

            ApiSingleton.getInstance(context!!).addToRequestQueue(requesterJsonObjectRequest)


        }else
            Toast.makeText(context, R.string.no_connection,Toast.LENGTH_SHORT).show()
    }
}