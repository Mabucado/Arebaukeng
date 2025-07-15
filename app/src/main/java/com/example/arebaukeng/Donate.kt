package com.example.arebaukeng

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Date
import java.util.Locale

class Donate : AppCompatActivity() {
    lateinit var amount:TextView
    lateinit var donate:Button
    lateinit var home:Button
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate)
         val viewModel = ViewModelProvider(this).get(DataViewModel::class.java)
                val model =viewModel.model
        amount=findViewById(R.id.edtDonAmount)
        donate=findViewById(R.id.btnDonate)
        home=findViewById(R.id.btnDonHome)
        var donationNo:Int
        if(model.donationTable.isNotEmpty()){
               donationNo=model.donationTable.map { it.donatNo }.last()+1
        }else{
            donationNo=601
        }

        donate.setOnClickListener {
         val amt = amount.text.toString().toInt()
            val calendar = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd ", Locale.getDefault())
          model.donationTable.add(Donation(model.currentUser, donationNo,dateFormat.format(calendar.time).toString(),amt))
            model.myRefDonation.setValue(model.donationTable)
            Toast.makeText(this, "Donation successfully received thank you.", Toast.LENGTH_SHORT).show()
            
        }
        home.setOnClickListener {
           val intent=Intent(this,Home::class.java)
            startActivity(intent)
        }

    }
}