package com.example.arebaukeng

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView

class Home : AppCompatActivity() {
    lateinit var listView:ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        var itemsList: MutableList<String> = mutableListOf<String>("Donate","Volunteer","Volunteer List","Chat","Request a ride","Requested rides","Make an appointment","Appointments","Request home treatment","Requested home treatments","Library")

        listView=findViewById(R.id.listView)
        val adapterV = ArrayAdapter<String>(
            this,
            android.R.layout.simple_dropdown_item_1line,
            itemsList
        )
        listView.adapter=adapterV

        listView.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val item = parent.getItemAtPosition(position) as String
                if(item=="Donate"){
                    val intent= Intent(this@Home,Donate::class.java)
                    startActivity(intent)

                }
                if(item=="Volunteer"){
                    val intent= Intent(this@Home,Volunteer::class.java)
                    startActivity(intent)

                }
                if(item=="Volunteer List"){
                    val intent= Intent(this@Home,DisplayVolunteer::class.java)
                    startActivity(intent)

                }
                if(item=="Chat"){
                    val intent= Intent(this@Home,Chats::class.java)
                    startActivity(intent)

                }
                if(item=="Request a ride"){
                    val intent= Intent(this@Home,Ride::class.java)
                    startActivity(intent)

                }
                if(item=="Requested rides"){
                    val intent= Intent(this@Home,DisplayRides::class.java)
                    startActivity(intent)

                }
                if(item=="Make an appointment"){
                    val intent= Intent(this@Home,Appointment::class.java)
                    startActivity(intent)

                }
                if(item=="Request home treatment"){
                    val intent= Intent(this@Home,HomeTreatment::class.java)
                    startActivity(intent)

                }
                if(item=="Appointments"){
                    val intent= Intent(this@Home,DisplayAppointments::class.java)
                    startActivity(intent)

                }
                if(item=="Requested home treatments"){
                    val intent= Intent(this@Home,RequestedHomeTreatment::class.java)
                    startActivity(intent)

                }
                if(item=="Library"){
                    val intent= Intent(this@Home,Library::class.java)
                    startActivity(intent)

                }

            }


            }





    }
}