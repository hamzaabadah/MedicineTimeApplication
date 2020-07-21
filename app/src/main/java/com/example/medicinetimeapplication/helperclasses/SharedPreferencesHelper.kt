package com.example.medicinetimeapplication.helperclasses

import android.content.Context
import com.example.medicinetimeapplication.model.Clinic
import com.example.medicinetimeapplication.model.Doctor
import com.example.medicinetimeapplication.model.NextVisitModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class SharedPreferencesHelper(var context: Context){
    val TAG = "tag_shared_preferences_helper"
    companion object {
        @Volatile
        private var INSTANCE: SharedPreferencesHelper? = null
        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: SharedPreferencesHelper(context).also {
                    INSTANCE = it
                }
            }
    }

    fun saveInSharedPref (context: Context,nameKey:String,value: String){
        val mPrefs = context.getSharedPreferences("save_in_shared", Context.MODE_PRIVATE)
        val prefsEditor = mPrefs.edit()
        prefsEditor.putString(nameKey,value).apply()
    }

    fun saveSharedPreferencesDoctorList(context: Context, doctor: ArrayList<Doctor>) {
        val mPrefs = context.getSharedPreferences("save_in_shared", Context.MODE_PRIVATE)
        val prefsEditor = mPrefs.edit()
        val gson = Gson()
        val json = gson.toJson(doctor)
        prefsEditor.putString("list_of_doctors", json)
        prefsEditor.apply()
    }

    fun saveSharedPreferencesClinicList(context: Context, clinic: ArrayList<Clinic>) {
        val mPrefs = context.getSharedPreferences("save_in_shared", Context.MODE_PRIVATE)
        val prefsEditor = mPrefs.edit()
        val gson = Gson()
        val json = gson.toJson(clinic)
        prefsEditor.putString("list_of_clinic", json)
        prefsEditor.apply()
    }

    fun loadSharedPreferencesLogList(context: Context): List<Doctor?>? {
        var callLog: List<Doctor?> = ArrayList<Doctor?>()

        val mPrefs = context.getSharedPreferences("save_in_shared", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = mPrefs.getString("list_of_doctors", "")
        callLog = if (json!!.isEmpty()) {
            ArrayList<Doctor?>()
        } else {
            val type: Type = object : TypeToken<List<Doctor?>?>() {}.type
            gson.fromJson(json, type)
        }
        return callLog
    }

    fun loadSharedPreferencesClinicList(context: Context): List<Clinic?>? {
        var clinic: List<Clinic?>

        val mPrefs = context.getSharedPreferences("save_in_shared", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = mPrefs.getString("list_of_clinic", "")
        clinic = if (json!!.isEmpty()) {
            ArrayList<Clinic?>()
        } else {
            val type: Type = object : TypeToken<List<Clinic?>?>() {}.type
            gson.fromJson(json, type)
        }
        return clinic
    }

    fun load(context: Context): NextVisitModel? {
        val mPrefs = context.getSharedPreferences("save_in_shared", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = mPrefs.getString("list_of_doctors", "")

        return gson.fromJson(json,NextVisitModel::class.java)
    }

}