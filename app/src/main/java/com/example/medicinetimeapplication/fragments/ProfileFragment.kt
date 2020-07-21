package com.example.medicinetimeapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.medicinetimeapplication.ProfileControllerActivity

import com.example.medicinetimeapplication.R
import com.example.medicinetimeapplication.apiclasses.Utility
import kotlinx.android.synthetic.main.fragment_profile.view.*

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        root.profile_fragment_edit_profile.setOnClickListener(){
            Utility.sendIdToReplacedProfileFragments(context!!,1)
        }

        root.profile_fragment_change_pass.setOnClickListener(){
            Utility.sendIdToReplacedProfileFragments(context!!,2)
        }

        root.profile_fragment_who_are_we.setOnClickListener(){
            Utility.sendIdToReplacedProfileFragments(context!!,3)
        }

        root.profile_fragment_logout.setOnClickListener(){
            Utility.sendIdToReplacedProfileFragments(context!!,4)
        }
        root.profile_fragment_terms_conditions.setOnClickListener{
            Utility.sendIdToReplacedProfileFragments(context!!,5)
        }
        return root
    }

}
