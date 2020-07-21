package com.example.medicinetimeapplication.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.medicinetimeapplication.NextVisitShowActivity
import com.example.medicinetimeapplication.R
import com.example.medicinetimeapplication.apiclasses.ApiController
import com.example.medicinetimeapplication.apiclasses.Utility
import com.example.medicinetimeapplication.helperclasses.SharedPreferencesHelper
import com.example.medicinetimeapplication.model.Clinic
import com.example.medicinetimeapplication.model.NextVisitModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.item_next_visit.view.*
import kotlinx.android.synthetic.main.item_our_clinic.view.*

class NextVisitAdapter (var context: Context, var data : ArrayList<NextVisitModel>, var root : View):
    RecyclerView.Adapter<NextVisitAdapter.MyViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.item_next_visit, parent, false)
        return MyViewHolder(root).listen{position, type ->
            val item = data[position]
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.clinicName.text = data[position].clinic?.name
        holder.doctorName.text = data[position].doctor?.doctorName
        holder.visitAt.text= data[position].patientVisit?.visitAt
    }

    private fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
        itemView.setOnClickListener {
            event.invoke(adapterPosition, itemViewType)
            val mPrefs = context.getSharedPreferences("save_in_shared", Context.MODE_PRIVATE)
            val prefsEditor = mPrefs.edit()
            val gson = Gson()
            val json = gson.toJson(data[position])
            Log.i("nextvisit",json)
            prefsEditor.putString("list_of_doctors", json)
            prefsEditor.apply()

            Utility.startNewActivityWithOutFinish(context,NextVisitShowActivity::class.java)
        }
        return this
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val clinicName = itemView.item_next_visit_id_clinic_name
        val doctorName = itemView.item_next_visit_id_doctor_name
        val visitAt = itemView.item_next_visit_id_visit_at
    }
}