package com.example.medicinetimeapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.medicinetimeapplication.model.MyDoctorModel
import kotlinx.android.synthetic.main.activity_patient_drug_info.*

class PatientDrugInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_drug_info)

        val myDoctorModel= intent.getParcelableExtra("myDoctorModel") as? MyDoctorModel
        patient_drug_info_activity_doctor_name.text=myDoctorModel?.doctorName
        patient_drug_info_activity_clinic_name.text=myDoctorModel?.clinicName

        patient_drug_info_activity_back_btn.setOnClickListener(){
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
