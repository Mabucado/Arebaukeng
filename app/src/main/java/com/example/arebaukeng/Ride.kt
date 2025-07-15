package com.example.arebaukeng

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Ride : AppCompatActivity() {
    lateinit var selection:TextView
    lateinit var radioGroup: RadioGroup
    lateinit var home:Button
    lateinit var request:Button
    lateinit var street:TextView
    lateinit var surburb:TextView
    lateinit var town:TextView
    lateinit var postalCode:TextView
    lateinit var requested:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ride)
        val viewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        val model =viewModel.model


        radioGroup = findViewById(R.id.rgDrivers)
        selection = findViewById(R.id.txtSelection)
        street  = findViewById(R.id.edtStreet)
        surburb  = findViewById(R.id.edtSurburb)
        town  =  findViewById(R.id.edtTown)
        postalCode = findViewById(R.id.edtPostal)
        request=findViewById(R.id.btnRequest)
        home=findViewById(R.id.btnRideHome)
        requested=findViewById(R.id.btnRequested)

        var items = model.volunteerTable.filter { it.volunteerType=="Driver" }.map{it.volunteerName}
// Loop through each item and create a RadioButton
for(item in items) {
    if (model.volunteerTable.map { it.volunteerType }.contains("Driver")) {

        val radioButton = RadioButton(this)
        radioButton.text = item
        radioGroup.addView(radioButton)
    } else {
        selection.text = "No available drivers"
    }
}
home.setOnClickListener {
    var intent= Intent(this,Home::class.java)
    startActivity(intent)
}

        request.setOnClickListener {
            val str= street.text.toString()
            val sur= surburb.text.toString()
            val town= town.text.toString()
           val pos= postalCode.text.toString()

            val selectedRadioButtonId = radioGroup.checkedRadioButtonId
            if (selectedRadioButtonId != -1) {
                val selectedRadioButton = findViewById<RadioButton>(selectedRadioButtonId)
                val selectedOption = selectedRadioButton.text.toString()
                var rideNo:Int
                if(model.rideTable.isNotEmpty()) {
                    rideNo = model.rideTable.map { it.rideID }.last() + 1
                }
                else{rideNo=101}
                val calendar = Calendar.getInstance()
                val dateFormat = SimpleDateFormat("HH:mm ", Locale.getDefault())
                model.rideTable.add(RideData(rideNo,model.currentUser,
                    mutableListOf( Address(str,sur,town,pos)),selectedOption,dateFormat.format(calendar.time)))
                model.myRefRide.setValue(model.rideTable)
                Toast.makeText(this, "You have requested a ride from $selectedOption \n they will contact you", Toast.LENGTH_SHORT).show()
            }

            else {
            Toast.makeText(this, "No option selected", Toast.LENGTH_LONG).show()
        }


        }
        requested.setOnClickListener {
            var intent=Intent(this,DisplayRides::class.java)
            startActivity(intent)
        }



    }
}