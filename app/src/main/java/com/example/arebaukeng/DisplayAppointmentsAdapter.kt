package com.example.arebaukeng

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class DisplayAppointmentsAdapter (private val userList: List<AppointmentList>) :
        RecyclerView.Adapter<DisplayAppointmentsAdapter.AppointmentViewHolder>() {


        class AppointmentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val nameTextView: TextView = view.findViewById(R.id.nameView)
            val nurseTextView: TextView = view.findViewById(R.id.nurseView)
            val timeTextView: TextView = view.findViewById(R.id.timetextView)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item__display_appointments, parent, false)
            return AppointmentViewHolder(view)

        }

        override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
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



data class AppointmentList(val name: String, val nurse: String, val time:String)

