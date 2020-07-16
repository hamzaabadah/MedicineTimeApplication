package com.example.medicinetimeapplication.patientfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.medicinetimeapplication.R
import com.example.medicinetimeapplication.apiclasses.authenticationpatient.ApiControllerAuthPatient
import kotlinx.android.synthetic.main.fragment_patient_log_in.view.*

/**
 * A simple [Fragment] subclass.
 */
class PatientLogInFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_patient_log_in, container, false)

        root.id_patent_log_in_fragment_btn_log_in.setOnClickListener(){
            ApiControllerAuthPatient.getInstance(context!!).loginPatient(root)
        }
        root.id_patent_log_in_fragment_register.setOnClickListener(){
            replaceFragment(RegisterFragment())
        }
        return root
    }

    private fun replaceFragment(fragment: Fragment){
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.activity_patient_container,
            fragment).addToBackStack(null).commit()
    }

}
