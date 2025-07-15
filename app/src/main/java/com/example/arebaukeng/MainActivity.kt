package com.example.arebaukeng

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.mindrot.jbcrypt.BCrypt

class MainActivity : AppCompatActivity() {
    lateinit var  login:    Button
    lateinit var  register: Button
    lateinit var  username: TextView
    lateinit var  password: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firebase.database.setPersistenceEnabled(true)
        val viewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        val model =viewModel.model
        viewModel.fetchUsers()





        setContentView(R.layout.activity_main)

        login=findViewById(R.id.btnLogin)
        register=findViewById(R.id.btnRegister)
        username=findViewById(R.id.edtUsername)
        password=findViewById(R.id.edtPassword)









        register.setOnClickListener {
            var intent= Intent(this,Register::class.java)
            startActivity(intent)

        }
        login.setOnClickListener {

            val user=username.text.toString()
            val pass=password.text.toString()
            if(model.userTable.map { it.username }.contains(user)){
                if(checkPassword(pass,model.userTable.map { it.password })){
                    model.currentUser=user
                    val intent=Intent(this,Home::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "Incorrect password", Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(this,"Incorrect username", Toast.LENGTH_SHORT).show()
            }


        }
    }
    fun checkPassword(password: String, hashedPasswordList: List<String>): Boolean {
        return hashedPasswordList.any { hashedPassword ->
            BCrypt.checkpw(password, hashedPassword)
        }
    }
}