package com.example.medicinetimeapplication.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.medicinetimeapplication.R
import com.example.medicinetimeapplication.apiclasses.doctorrequest.ApiControllerDoctor
import com.example.medicinetimeapplication.helperclasses.SharedPreferencesHelper

/**
 * A simple [Fragment] subclass.
 */
class DashboardFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        ApiControllerDoctor.getInstance(context!!).getAuthenticatedPatientDoctors(root)
        return root
    }

}
