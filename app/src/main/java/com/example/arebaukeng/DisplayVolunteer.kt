package com.example.arebaukeng

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DisplayVolunteer : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    private lateinit var gestureDetector: GestureDetector
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_volunteer)
         recyclerView = findViewById(R.id.recyclerView)
        val viewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        val model =viewModel.model

        val adapter = VolunteerAdapter(createUserList(model.volunteerTable.map{it.volunteerName},model.volunteerTable.map{it.volunteerType},model.userTable.find{it.username==model.currentUser}!!.phone))
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        gestureDetector = GestureDetector(this, SwipeGestureListener {
            // Handle the swipe up action
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        })

        val swipeView: View = findViewById(R.id.swipe_view)
        swipeView.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
        }
    }

    private fun createUserList(names: List<String>, types: List<String>, numbers: String): List<VolunteerList> {
        val userList = mutableListOf<VolunteerList>()

        for (i in names.indices) {
            userList.add(VolunteerList(names[i], types[i], numbers ))
        }
        return userList
    }
override fun onTouchEvent(event: MotionEvent): Boolean {
    return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event)
}
}
