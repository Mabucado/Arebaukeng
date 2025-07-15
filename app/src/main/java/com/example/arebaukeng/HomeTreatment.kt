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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeTreatment : AppCompatActivity() {
    lateinit var home: Button
    lateinit var patients: Button
    lateinit var submit: Button
    lateinit var street: TextView
    lateinit var surburb: TextView
    lateinit var town: TextView
    lateinit var postal: TextView
    lateinit var name: TextView
    lateinit var reason: TextView
    lateinit var radioGroup: RadioGroup
    lateinit var available: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_treatment)
        home = findViewById(R.id.btnTreatHome)
        submit = findViewById(R.id.btnTreatSubmit)
        patients = findViewById(R.id.btnTreatView)
        street = findViewById(R.id.edtTreatStreet)
        surburb = findViewById(R.id.edtTreatSurburb)
        town = findViewById(R.id.edtTreatTown)
        postal = findViewById(R.id.edtTreatTown)
        name = findViewById(R.id.edtTreatName)
        reason = findViewById(R.id.edtTreatReason)
        radioGroup = findViewById(R.id.rgAvailNurse)
        available = findViewById(R.id.txtDisplayAvail)

        val viewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        val model = viewModel.model

        var items =
            model.volunteerTable.filter { it.volunteerType == "Nurse" }.map { it.volunteerName }
// Loop through each item and create a RadioButton
        Log.i(
            "Containing Nurse",
            model.volunteerTable.map { it.volunteerType }.contains("Nurse").toString()
        )
        for (item in items) {
            if (model.volunteerTable.map { it.volunteerType }.contains("Nurse")) {

                val radioButton = RadioButton(this)
                radioButton.text = item
                radioGroup.addView(radioButton)
            } else {
                available.text = "No available drivers"
                Log.i(
                    "False statement",
                    model.volunteerTable.map { it.volunteerType }.contains("Nurse").toString()
                )

            }
        }

        patients.setOnClickListener {
            var intent= Intent(this,RequestedHomeTreatment::class.java)
            startActivity(intent)
        }

        home.setOnClickListener {
            var intent = Intent(this, Home::class.java)
            startActivity(intent)
        }
        submit.setOnClickListener {
            val str = street.text.toString()
            val sur = surburb.text.toString()
            val town = town.text.toString()
            val pos = postal.text.toString()
            var name = name.text.toString()
            val res = reason.text.toString()

            val selectedRadioButtonId = radioGroup.checkedRadioButtonId
            if (selectedRadioButtonId != -1) {
                val selectedRadioButton = findViewById<RadioButton>(selectedRadioButtonId)
                val selectedOption = selectedRadioButton.text.toString()
                var treatNo: Int
                if (model.treatTable.isNotEmpty()) {
                    treatNo = model.treatTable.map { it.treatId }.last() + 1

                } else {
                    treatNo = 501
                }
                val calendar = Calendar.getInstance()
                val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                model.treatTable.add(
                    TreatmentData(
                        treatNo,
                        name,
                        res,
                        dateFormat.format(calendar.time),
                        selectedOption,
                        mutableListOf( Address(str,sur,town,pos))
                    )
                )
                model.myRefTreatment.setValue(model.treatTable)

                Toast.makeText(
                    this,
                    "You have requested home treatemnt nurse \n $selectedOption will be in contact",
                    Toast.LENGTH_LONG
                ).show()


            } else {
                Toast.makeText(this, "No option selected", Toast.LENGTH_LONG).show()
            }

        }
    }
}