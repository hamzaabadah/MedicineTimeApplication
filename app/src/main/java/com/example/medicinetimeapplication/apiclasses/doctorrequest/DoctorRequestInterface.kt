package com.example.medicinetimeapplication.apiclasses.doctorrequest

import android.view.View

interface DoctorRequestInterface {
    fun getAuthenticatedPatientDoctors(root: View)
    fun getAuthenticatedPatientDoctorDetails(root: View,idDoctor:Int)
}