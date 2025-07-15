package com.example.arebaukeng

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView



class  RequestedTreatmentAdapter(private val userList: List<TreatList>) :
        RecyclerView.Adapter< RequestedTreatmentAdapter.TreatViewHolder>() {


        class TreatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val nameTextView: TextView = view.findViewById(R.id.nameTextView)
            val nurseTextView: TextView = view.findViewById(R.id.nurseView)
            val timeTextView: TextView = view.findViewById(R.id.timeView)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TreatViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_requested_treatment, parent, false)
            Log.i("Adapter", "Adapter inflated")
            return TreatViewHolder(view)

        }

        override fun onBindViewHolder(holder:TreatViewHolder, position: Int) {
            val user = userList[position]
            Log.i("List size", userList.size.toString())
            holder.nameTextView.text = user.name
            holder.nurseTextView.text = user.nurse
            holder.timeTextView.text = user.time
        }

        override fun getItemCount(): Int {
            return userList.size
        }
    }



    data class TreatList(val name: String, val nurse: String, val time:String)


