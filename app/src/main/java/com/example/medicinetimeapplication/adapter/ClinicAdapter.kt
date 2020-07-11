package com.example.medicinetimeapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.medicinetimeapplication.R
import com.example.medicinetimeapplication.apiclasses.ApiController
import com.example.medicinetimeapplication.model.Clinic
import kotlinx.android.synthetic.main.item_our_clinic.view.*
import java.lang.reflect.Method

class ClinicAdapter (var context: Context , var data : ArrayList<Clinic>, var root :View):
    RecyclerView.Adapter<ClinicAdapter.MyViewHolder>(){

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val id_item_our_clinic_name = itemView.id_item_our_clinic_name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.item_our_clinic, parent, false)
        return MyViewHolder(root).listen{position, type ->
            val item = data[position]
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.id_item_our_clinic_name.text = data[position].name
    }

    private fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
        itemView.setOnClickListener {
            event.invoke(adapterPosition, itemViewType)
            Toast.makeText(context,"hsa", Toast.LENGTH_SHORT).show()
            ApiController.getInstance(context!!).requester(context , root, "")
        }
        return this
    }
}