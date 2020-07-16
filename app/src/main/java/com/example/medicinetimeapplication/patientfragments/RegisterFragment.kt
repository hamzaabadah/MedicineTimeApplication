package com.example.medicinetimeapplication.patientfragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.medicinetimeapplication.R
import com.example.medicinetimeapplication.apiclasses.Utility
import com.example.medicinetimeapplication.apiclasses.authenticationpatient.ApiControllerAuthPatient
import kotlinx.android.synthetic.main.fragment_register.view.*

/**
 * A simple [Fragment] subclass.
 */
class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_register, container, false)

        root.patent_register_fragment_id_birth_date_relative_layout.setOnClickListener(){
            Utility.setBirthDate(context!!,root)
        }
        root.patent_register_fragment_id_layout_gender.setOnClickListener(){
            Utility.openDialog(context!!,root)
        }
        root.patent_register_fragment_id_btn_register.setOnClickListener(){
            ApiControllerAuthPatient.getInstance(context!!).registerPatient(root)
        }
        return root
    }

}
