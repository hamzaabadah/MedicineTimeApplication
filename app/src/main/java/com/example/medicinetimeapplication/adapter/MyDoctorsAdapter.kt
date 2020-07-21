package com.example.medicinetimeapplication.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.medicinetimeapplication.R
import com.example.medicinetimeapplication.apiclasses.Utility
import com.example.medicinetimeapplication.model.Doctor
import com.example.medicinetimeapplication.model.MyDoctorModel
import kotlinx.android.synthetic.main.item_my_doctors.view.*


class MyDoctorsAdapter (var context: Context, var data : ArrayList<MyDoctorModel>, var root : View):
    RecyclerView.Adapter<MyDoctorsAdapter.MyViewHolder>(){

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val doctorName = itemView.item_my_doctors_doctor_name
        val clinicName=itemView.item_my_doctors_clinic_name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.item_my_doctors, parent, false)
        return MyViewHolder(root).listen{position, type ->
            val item = data[position]
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.doctorName.text=data[position].doctorName
        holder.clinicName.text=data[position].clinicName
    }

    private fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
        itemView.setOnClickListener {
            event.invoke(adapterPosition, itemViewType)
            var myDoctorModel= MyDoctorModel(data[position].idDoctor,data[position].doctorName,data[position].idClinic,data[position].clinicName)
            Utility.launchNextScreen(context,myDoctorModel)
        }
        return this
    }
}