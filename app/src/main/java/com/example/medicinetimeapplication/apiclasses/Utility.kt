package com.example.medicinetimeapplication.apiclasses

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.medicinetimeapplication.PatientDrugInfoActivity
import com.example.medicinetimeapplication.ProfileControllerActivity
import com.example.medicinetimeapplication.R
import com.example.medicinetimeapplication.model.Doctor
import com.example.medicinetimeapplication.model.MyDoctorModel
import kotlinx.android.synthetic.main.fragment_register.view.*
import java.util.*


object Utility {
    // Check if connected to internet
    fun isOnline(context: Context): Boolean {
        val connMgr =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    fun startNewActivityWithOutFinish(context: Context, clazz: Class<*>) {
        val intent = Intent(context, clazz)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun launchNextScreen(context: Context, myDoctorModel:  MyDoctorModel): Intent {
        val intent = Intent(context, PatientDrugInfoActivity::class.java)
        intent.putExtra("myDoctorModel", myDoctorModel)
        context.startActivity(intent)
        return intent
    }

    fun sendIdToReplacedProfileFragments(context: Context , id:Int):Intent{
        val intent = Intent(context, ProfileControllerActivity::class.java)
        intent.putExtra("id_replaced_fragment",id)
        context.startActivity(intent)
        return intent
    }

    fun startNewActivity(context: Context, clazz: Class<*>) {
        val intent = Intent(context, clazz)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        (context as Activity).finish()
    }

    fun openDialog (context: Context , root :View){
        var gender= ""!!
        val male = R.string.gender_type_male
        val female = R.string.gender_type_female
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle(R.string.patent_register_fragment_title_dialog)
        alertDialog.setCancelable(false)

        alertDialog.setPositiveButton(male) { dialogInterface, i ->
            gender="male"
            root.patent_register_fragment_id_gender.text=gender
            Log.i("open_dialog",gender)
        }
        alertDialog.setNegativeButton(female) { _, _ ->
            gender= "female"
            root.patent_register_fragment_id_gender.text= gender
            Log.i("open_dialog",gender)
        }
        alertDialog.setNeutralButton(R.string.gender_type_chanel) { dialogInterface, i ->

        }
        alertDialog.create().show()
    }

    fun setBirthDate (context: Context , root: View){
        val mcurrentDate = Calendar.getInstance()
        val day = mcurrentDate.get(Calendar.DAY_OF_MONTH)
        val month = mcurrentDate.get(Calendar.MONTH)
        val year = mcurrentDate.get(Calendar.YEAR)

        val picker = DatePickerDialog(context, DatePickerDialog.OnDateSetListener {
                view, year, monthOfYear, dayOfMonth ->

            val month = monthOfYear + 1
            var formattedMonth = "" + month
            var formattedDayOfMonth = "" + dayOfMonth

            if (month < 10) {
                formattedMonth = "0$month"
            }
            if (dayOfMonth < 10) {
                formattedDayOfMonth = "0$dayOfMonth"
            }
                root.patent_register_fragment_id_birth_date.text = """$year-${formattedMonth}-$formattedDayOfMonth"""
            }, year, month, day
        )



        picker.show()
    }
}