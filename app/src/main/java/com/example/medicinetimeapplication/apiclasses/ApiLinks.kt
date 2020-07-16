package com.example.medicinetimeapplication.apiclasses

object ApiLinks {

    private const val BASE_URL = "http://medicine.effect.ps/api/v1"
    val LOGIN_URL = "$BASE_URL/patient/login"
    val REGISTER_URL = "$BASE_URL/patient/register"

    val PATIENT_INFO_URL ="$BASE_URL/patient/info"
}