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

class DisplayRides : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    private lateinit var gestureDetector: GestureDetector
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_rides)
        val viewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        val model =viewModel.model

        recyclerView=findViewById(R.id.recyclerView)

        val adapter = DisplayRidesAdapter(model.rideTable.map { ride ->RiderList(ride.username,ride.driver, ride.timeStamp)})
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
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event)
    }
    private fun createUserList(names: List<String>, driver: List<String>, times: List<String>): List<RiderList> {
        val userList = mutableListOf<RiderList>()

        for (i in names.indices) {
            userList.add(RiderList(names[i], driver[i], times[i] ))
        }
        return userList
    }
}