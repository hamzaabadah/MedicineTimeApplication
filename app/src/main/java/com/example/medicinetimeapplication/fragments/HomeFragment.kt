package com.example.medicinetimeapplication.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.medicinetimeapplication.R
import com.example.medicinetimeapplication.adapter.NextVisitAdapter
import com.example.medicinetimeapplication.apiclasses.patientrequests.ApiControllerPatient
import com.example.medicinetimeapplication.helperclasses.SharedPreferencesHelper
import com.example.medicinetimeapplication.model.Clinic
import com.example.medicinetimeapplication.model.Doctor
import com.example.medicinetimeapplication.model.NextVisitModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.lang.reflect.Type

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        ApiControllerPatient.getInstance(context!!).getAuthenticatedPatientInfo(root)

        return root
    }

}
