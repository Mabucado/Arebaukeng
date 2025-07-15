package com.example.arebaukeng

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider

class Volunteer : AppCompatActivity() {
    lateinit var name:TextView
    lateinit var gender:TextView
    lateinit var nurse:RadioButton
    lateinit var driver:RadioButton
    lateinit var cook:RadioButton
    lateinit var volunteer:Button
    lateinit var viewList:Button
    lateinit var home:Button
    lateinit var radioGroup:RadioGroup
    private val SMS_PERMISSION_CODE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_volunteer)
        val viewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        val model =viewModel.model

        name=findViewById(R.id.edtVolName)
        gender=findViewById(R.id.edtVolGen)
        nurse=findViewById(R.id.rbNurse)
        driver=findViewById(R.id.rbDriver)
        volunteer=findViewById(R.id.btnVolunteer)
        cook=findViewById(R.id.rbCook)
        viewList=findViewById(R.id.btnVolList)
        home=findViewById(R.id.btnVolHome)
        radioGroup=findViewById(R.id.radioGroup)

        home.setOnClickListener {
            val intent= Intent(this,Home::class.java)
            startActivity(intent)
        }
        volunteer.setOnClickListener {
            val names=name.text.toString()
            val genders=gender.text.toString()
            val selectedRadioButtonId = radioGroup.checkedRadioButtonId
            if (selectedRadioButtonId != -1) {
                val selectedRadioButton = findViewById<RadioButton>(selectedRadioButtonId)
                val selectedOption = selectedRadioButton.text.toString()
                var voluntNo:Int
                if(model.volunteerTable.isNotEmpty()) {
                     voluntNo = model.volunteerTable.map { it.volunNo }.last() + 1
                }
                else{voluntNo=101}
                model.volunteerTable.add(Volunteers(model.currentUser,voluntNo,names,genders,selectedOption))
                Toast.makeText(this, "You have volunteered to be a $selectedOption", Toast.LENGTH_LONG).show()
                model.myRefVolunteer.setValue(model.volunteerTable)
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    val smsManager: SmsManager = SmsManager.getDefault()
                    val phoneNumber= model.userTable.find { it.username==model.currentUser }!!.phone
                    val message="You have volunteered to be a $selectedOption at Arebaukeng you will be contacted soon"
                    smsManager.sendTextMessage(phoneNumber, null, message, null, null)
                    Log.i("Notify text","The phone number is $phoneNumber")

                } else {
                    ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.SEND_SMS), SMS_PERMISSION_CODE)
                }

            } else {
                Toast.makeText(this, "No option selected", Toast.LENGTH_LONG).show()
            }


        }
        viewList.setOnClickListener {
            val intent=Intent(this,DisplayVolunteer::class.java)
            startActivity(intent)
        }

        




    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == SMS_PERMISSION_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // Permission granted, you can send SMS now
            } else {
                Toast.makeText(this, "Permission denied to send SMS", Toast.LENGTH_SHORT).show()
            }
        }
    }
}