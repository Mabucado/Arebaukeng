package com.example.arebaukeng

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

class Appointment : AppCompatActivity() {
    lateinit var home: Button
    lateinit var view:Button
    lateinit var submit:Button
    lateinit var available:TextView
    lateinit var time:TextView
    lateinit var descrption:TextView
    lateinit var name:TextView
    lateinit var radioGroup: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment)
        val viewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        val model =viewModel.model

        home=findViewById(R.id.btnAppHome)
        view=findViewById(R.id.btnView)
        submit=findViewById(R.id.btnSubmit)
        time=findViewById(R.id.edtAppTime)
        available=findViewById(R.id.txtAvailable)
        descrption=findViewById(R.id.edtDescription)
        name=findViewById(R.id.edtAppName)
        radioGroup=findViewById(R.id.rgNurse)

        var items = model.volunteerTable.filter { it.volunteerType=="Nurse" }.map{it.volunteerName}
// Loop through each item and create a RadioButton
        Log.i("Containing Nurse",model.volunteerTable.map { it.volunteerType }.contains("Nurse").toString())
        for(item in items) {
            if (model.volunteerTable.map { it.volunteerType }.contains("Nurse")) {

                val radioButton = RadioButton(this)
                radioButton.text = item
                radioGroup.addView(radioButton)
            } else {
                available.text = "No available drivers"
                Log.i("False statement",model.volunteerTable.map { it.volunteerType }.contains("Nurse").toString())

            }
        }
        home.setOnClickListener {
            var intent= Intent(this,Home::class.java)
            startActivity(intent)
        }
        view.setOnClickListener {
            var intent= Intent(this,DisplayAppointments::class.java)
            startActivity(intent)


        }
        submit.setOnClickListener {
            var time=time.text.toString()
            var ava=available.text.toString()
            var des=descrption.text.toString()
            var name=name.text.toString()
            val selectedRadioButtonId = radioGroup.checkedRadioButtonId
            if (selectedRadioButtonId != -1) {
                val selectedRadioButton = findViewById<RadioButton>(selectedRadioButtonId)
                val selectedOption = selectedRadioButton.text.toString()
                var appNo: Int
                if (model.appointmentTable.isNotEmpty()) {
                    appNo = model.appointmentTable.map { it.appointmentId }.last() + 1

                } else {
                    appNo = 301
                }
                model.appointmentTable.add(AppointmentData(appNo, name, time, des,selectedOption))
                model.myRefAppointment.setValue(model.appointmentTable)
                Toast.makeText(this, "You have made appointment to see nurse $selectedOption at $time", Toast.LENGTH_LONG).show()


            } else {
                Toast.makeText(this, "No option selected", Toast.LENGTH_LONG).show()
            }

        }

    }
}