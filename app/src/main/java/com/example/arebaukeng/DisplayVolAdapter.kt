package com.example.arebaukeng

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class VolunteerAdapter(private val userList: List<VolunteerList>) : RecyclerView.Adapter<VolunteerAdapter.UserViewHolder>() {

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        val typeTextView: TextView = view.findViewById(R.id.typeTextView)
        val numberTextView: TextView = view.findViewById(R.id.numberTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_volunteer, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.nameTextView.text = user.name
        holder.typeTextView.text = user.type
        holder.numberTextView.text = user.number
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}
data class VolunteerList(val name: String, val type: String, val number:String)
