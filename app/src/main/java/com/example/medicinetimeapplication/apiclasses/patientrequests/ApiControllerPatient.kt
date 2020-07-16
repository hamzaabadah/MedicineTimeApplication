package com.example.medicinetimeapplication.apiclasses.patientrequests

import android.content.Context
import android.view.View
import com.example.medicinetimeapplication.apiclasses.authenticationpatient.ApiControllerAuthPatient

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

    }
}