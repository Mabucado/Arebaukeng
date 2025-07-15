package com.example.arebaukeng

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt

class Register : AppCompatActivity() {
    lateinit var username: TextView
    lateinit var name: TextView
    lateinit var surname: TextView
    lateinit var age: TextView
    lateinit var address: TextView
    lateinit var email: TextView
    lateinit var phone: TextView
    lateinit var password: TextView
    lateinit var confirm: TextView
    lateinit var login: Button
    lateinit var register: Button
    lateinit var viewModel: DataViewModel
    lateinit var model: Model

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        viewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        model = viewModel.model


        username = findViewById(R.id.edtRegUsername)
        name = findViewById(R.id.edtRegName)
        surname = findViewById(R.id.edtRegSurname)
        age = findViewById(R.id.edtRegAge)
        address = findViewById(R.id.edtRegAddress)
        email = findViewById(R.id.edtRegEmail)
        password = findViewById(R.id.edtRegPassword)
        confirm = findViewById(R.id.edtRegConfirm)
        phone = findViewById(R.id.edtRegPhone)
        login = findViewById(R.id.btnRegLogin)
        register = findViewById(R.id.btnRegRegister)

        login.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        register.setOnClickListener {
            validateInputs()


        }
    }


    private fun validateInputs() {
        val names = name.text.toString().trim()
        val usernames = username.text.toString().trim()
        val surnames = surname.text.toString().trim()
        val ages = age.text.toString().toInt()
        val number = phone.text.toString().trim()
        val emails = email.text.toString().trim()
        val addr = address.text.toString().trim()
        val passwords = password.text.toString().trim()
        val confirming = confirm.text.toString().trim()
        val numberInt: Int

        if (usernames.isEmpty()) {
            username.error = "Username required"
            return
        }
        if(model.userTable.map { it.username }.contains(usernames)){
            username.error = "Username already exists"
            return
        }
        if (names.isEmpty()) {
            name.error = "Username required"
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emails).matches()) {
            email.error = "Enter a valid email address"
            return
        }
        if (addr.isEmpty()) {
            address.error = "Address is required"
            return
        }

        if (number.length != 10) {
            phone.error = "You have entered an inncorrect phone number"
            return
        }
        if (passwords.length < 8) {
            password.error = "Password must be at least 8 characters long"
            return
        }
        if (passwords != confirming) {
            confirm.error = "Password do not match"
            return
        }
        val hashPas = hashPassword(passwords)


        // If all inputs are valid, proceed with registration process
        registerUser(usernames, names, surnames, ages, addr, emails, number, hashPas)
    }

    private fun registerUser(
        username: String,
        name: String,
        surname: String,
        age: Int,
        address: String,
        email: String,
        phone: String,
        password: String
    ) {
        // Implement registration logic here
        // This method will be called when all inputs are valid
        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
        // You can navigate to the next screen or perform other actions after registration

        model.userTable.add(User(username, name, surname, email, address, age, phone, password))
        model.myRef.setValue(model.userTable)
        GlobalScope.launch(Dispatchers.Main) {
            delay(3000) // Delay for 3000 milliseconds (3 seconds)
            val intent = Intent(this@Register, MainActivity::class.java)
            startActivity(intent)

        }

    }
    fun hashPassword(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }
}