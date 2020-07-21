package com.example.medicinetimeapplication

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.medicinetimeapplication.helperclasses.SharedPreferencesHelper
import kotlinx.android.synthetic.main.activity_next_visit_show.*


class NextVisitShowActivity : AppCompatActivity() {

    private val REQUEST_CALL = 1
    var clinicPhone:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next_visit_show)



        var nextVisitModel = SharedPreferencesHelper.getInstance(this).load(this)
        if (nextVisitModel!=null){
            var name=nextVisitModel.clinic?.name
            var clinicAddress = nextVisitModel.clinic?.address
            clinicPhone= nextVisitModel.clinic?.phone.toString()

            var doctorName = nextVisitModel.doctor?.doctorName
            var medicalReview= nextVisitModel.patientVisit?.visitAt

            if (name!=null && clinicAddress!=null && clinicPhone!=null && doctorName!=null && medicalReview!=null){
                next_visit_activity_id_clinic_name.text = name
                next_visit_activity_id_clinic_address.text=clinicAddress
                next_visit_activity_id_clinic_phone.text=clinicPhone

                next_visit_activity_id_doctor_name.text=doctorName
                next_visit_activity_id_medical_review_time.text=medicalReview
            }else{
                onBackPressed()
            }
        }else onBackPressed()

        next_visit_activity_id_clinic_call.setOnClickListener(){
            makePhoneCall()
        }


        next_visit_activity_id_back_btn.setOnClickListener(){
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val mPrefs = this.getSharedPreferences("save_in_shared", Context.MODE_PRIVATE)
        val prefsEditor = mPrefs.edit()
        prefsEditor.clear()
        finish()
    }

    private fun makePhoneCall() {
        val number: String = clinicPhone
        if (number.trim { it <= ' ' }.isNotEmpty()) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    REQUEST_CALL
                )
            } else {
                val dial = "tel:$number"
                startActivity(Intent(Intent.ACTION_CALL, Uri.parse(dial)))
            }
        } else {
            Toast.makeText(this, "Enter Phone Number", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall()
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show()
            }
        }
    }



}

