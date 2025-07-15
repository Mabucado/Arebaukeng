package com.example.arebaukeng

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DisplayRidesAdapter(private val userList: List<RiderList>) : RecyclerView.Adapter<DisplayRidesAdapter.RideViewHolder>() {




        class RideViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val nameTextView: TextView = view.findViewById(R.id.nameView)
            val driverTextView: TextView = view.findViewById(R.id.drivertextView)
            val timeTextView: TextView = view.findViewById(R.id.timetextView)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RideViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_display_rides, parent, false)
            return RideViewHolder(view)

        }

        override fun onBindViewHolder(holder: RideViewHolder, position: Int) {
            val user = userList[position]
            Log.i("List size",userList.size.toString())
            holder.nameTextView.text = user.name
            holder.driverTextView.text = user.driver
            holder.timeTextView.text = user.time
        }

        override fun getItemCount(): Int {
            return userList.size
        }
    }



data class RiderList(val name: String, val driver: String, val time:String)