package com.example.arebaukeng

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Model(
    val userTable:MutableList<User> = mutableListOf(),
    val donationTable:MutableList<Donation> = mutableListOf(),
    val volunteerTable:MutableList<Volunteers> = mutableListOf(),
    val messageTable:MutableList<Message> = mutableListOf(),
    val rideTable:MutableList<RideData> =mutableListOf(),
    val appointmentTable:MutableList<AppointmentData> = mutableListOf(),
    val treatTable:MutableList<TreatmentData> =mutableListOf(),
    val bookTable: MutableMap<String, BookData> = mutableMapOf()


)
{

    val database = Firebase.database("https://arebaukeng-default-rtdb.europe-west1.firebasedatabase.app")
    val db = Firebase.firestore
    val myRef = database.getReference("userTable")
    val myRefDonation = database.getReference("donationTable")
    val myRefVolunteer = database.getReference("volunteerTable")
    val myRefMessage = database.getReference("messageTable")
    val myRefRide = database.getReference("rideTable")
    val myRefAppointment = database.getReference("appointmentTable")
    val myRefTreatment = database.getReference("treatmentTable")
    val myRefBook = db.collection("bookTable")

    lateinit var currentUser:String


}

data class TreatmentData(
    var treatId:Int=0,
    var name:String="",
    var reason:String="",
    var timeStamp: String="",
    var nurse:String="",
    var address:MutableList<Address> =mutableListOf(),
)
data class User(
        var username:String="",
        var name:String="",
        var surname:String="",
    var email:String="",
    var address:String="",
    var age:Int=0,
        var phone:String="",
    var password:String=""

        )
data class Donation(
    var username:String="",
    var donatNo:Int=101,
    var date:String="",
    var amount:Int=0


)
data class Volunteers(
    var username:String="",
    var volunNo:Int=201,
    var volunteerName:String="",
    var volunteerGender:String="",
    var volunteerType:String=""

)
data class Message(
    var username: String="",
    var content: String ="",
    var timeStamp:String="",
    var isSent: Boolean= null == true // true if sent by the user, false if received
)
data class RideData(
    var rideID:Int=0,
    var username: String="",
    var address:MutableList<Address> =mutableListOf(),
    var driver:String="",
    var timeStamp:String=""
)
data class Address(
    var street:String="",
    var surburb:String="",
    var town:String="",
    var postalCode:String=""
)
data class AppointmentData(
    var appointmentId:Int=0,
    var name:String="",
    var time:String="",
    var description:String="",
    var nurse:String=""

)
data class BookData(
    var image:Uri?=null,
    var pdf:Uri?=null
)
data class Book(
    var name:String="",
    var image:String="",
    var pdf:String=""
)

val globalModel = Model()

class DataViewModel : ViewModel() {
    val model = globalModel
    fun fetchUsers() {
        model.myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val users = mutableListOf<User>()

                for (snapshot in dataSnapshot.children) {
                    val user = snapshot.getValue(User::class.java)
                    if (user != null) {
                        users.add(user)
                    }


                }
                model.userTable.clear()
                model.userTable.addAll(users)


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle possible errors.
            }
        })
        model.myRefDonation.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val donations = mutableListOf<Donation>()
                for (snapshot in dataSnapshot.children) {
                    val donation = snapshot.getValue(Donation::class.java)
                    if (donation != null) {
                        donations.add(donation)
                    }
                }

                model.donationTable.clear()
                model.donationTable.addAll(donations)
            }
                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle possible errors.
                }

        })
        model.myRefVolunteer.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val volunteers = mutableListOf<Volunteers>()
                for (snapshot in dataSnapshot.children) {
                    val volunteer = snapshot.getValue(Volunteers::class.java)
                    if (volunteer != null) {
                        volunteers.add(volunteer)
                    }
                }

                model.volunteerTable.clear()
                model.volunteerTable.addAll(volunteers)

            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Handle possible errors.
            }
        })
        model.myRefMessage.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val messaages = mutableListOf<Message>()

                for (snapshot in dataSnapshot.children) {
                    val message = snapshot.getValue(Message::class.java)
                    if (message != null) {
                        messaages.add(message)
                    }


                }
                model.messageTable.clear()
                model.messageTable.addAll(messaages)


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle possible errors.
            }
        })
        model.myRefRide.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val rides = mutableListOf<RideData>()

                for (snapshot in dataSnapshot.children) {
                    val ride = snapshot.getValue(RideData::class.java)
                    if (ride != null) {
                        rides.add(ride)
                    }


                }
                model.rideTable.clear()
                model.rideTable.addAll(rides)


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle possible errors.
            }
        })
        model.myRefAppointment.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val appointments = mutableListOf<AppointmentData>()

                for (snapshot in dataSnapshot.children) {
                    val appointment = snapshot.getValue(AppointmentData::class.java)
                    if (appointment != null) {
                        appointments.add(appointment)
                    }


                }
                model.appointmentTable.clear()
                model.appointmentTable.addAll(appointments)


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle possible errors.
            }
        })
        model.myRefTreatment.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val treatments = mutableListOf<TreatmentData>()
                for (snapshot in dataSnapshot.children) {
                    val treatment = snapshot.getValue(TreatmentData::class.java)
                    if (treatment != null) {
                        treatments.add(treatment)
                    }
                }

                model.treatTable.clear()
                model.treatTable.addAll(treatments)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Handle possible errors.
            }

        })


    }
}