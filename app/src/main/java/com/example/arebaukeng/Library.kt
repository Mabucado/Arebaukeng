package com.example.arebaukeng

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import android.provider.Settings

class Library : AppCompatActivity() {
    lateinit var add: Button
    lateinit var home: Button
    lateinit var name: TextView
    lateinit var gridView: GridView
    lateinit var uploadPDF: Button
    lateinit var uploadImage: Button
    private var image: Uri? = null
    private var pdf: Uri? = null
    lateinit var model: Model

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library)
        val viewModel = ViewModelProvider(this).get(DataViewModel::class.java)
        model = viewModel.model
        add = findViewById(R.id.btnDonateBook)
        home = findViewById(R.id.btnLibHome)
        name = findViewById(R.id.edtLibName)
        gridView = findViewById(R.id.gridView)
        uploadImage = findViewById(R.id.btnImage)
        uploadPDF = findViewById(R.id.btnPDF)

        val bookList = model.bookTable.map { (name, bookData) ->
            ImageTextModel(bookData.image, name)
        }

        val adapter = LibraryAdapter(this, bookList)
        gridView.adapter = adapter

        add.setOnClickListener {
            val nameB = name.text.toString()

            if (image != null && pdf != null) {
                checkPermissions()
                uploadBook()
                Log.i("Storage", "You have stored")

                val bookList = model.bookTable.map { (name, bookData) ->
                    ImageTextModel(bookData.image, name)
                }

                val adapter = LibraryAdapter(this, bookList)
               gridView.adapter = adapter

            } else {
                Log.i("Storage ", "Not successful")
            }


        }
        uploadPDF.setOnClickListener {
            val intent1 = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                type = "application/pdf"
                addCategory(Intent.CATEGORY_OPENABLE)
            }
            startActivityForResult(intent1, PDF_REQUEST_CODE)
        }
        uploadImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                type = "image/*"
                addCategory(Intent.CATEGORY_OPENABLE)
            }
            startActivityForResult(intent, IMAGE_REQUEST_CODE)
            add.isEnabled = true
        }
        home.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }
        gridView.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val item = parent.getItemAtPosition(position) as ImageTextModel
                Toast.makeText(this@Library, "Clicked: ${item.text}", Toast.LENGTH_SHORT).show()

                val pdf = model.bookTable[item.text]?.pdf

                if (pdf != null) {
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        setDataAndType(pdf, "application/pdf")
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    }
                    startActivity(intent)
                } else {
                    Toast.makeText(this@Library, "PDF not available", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            when (requestCode) {
                IMAGE_REQUEST_CODE -> {
                    val selectedImageUri = data.data
                    image = selectedImageUri

                }
            }
        }
        if (resultCode == Activity.RESULT_OK && data != null) {
            when (requestCode) {
                PDF_REQUEST_CODE -> {
                    val selectedPdfUri = data.data
                    pdf = selectedPdfUri
                }
            }
        }
    }


    companion object {
        private const val IMAGE_REQUEST_CODE = 2
        private const val PDF_REQUEST_CODE = 1
        private val REQUEST_CODE_PERMISSIONS = 1001
    }

    private fun uploadBook() {
        val bookName = name.text.toString().trim()
        if (bookName.isEmpty() || image == null || pdf == null) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }
        model.bookTable[bookName] = BookData(image,pdf)


       model.myRefBook.add(model.bookTable) .addOnSuccessListener { documentReference ->
                Log.d("Upload","DocumentSnapshot added with ID: ${documentReference.id}")
            }

    }


    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) ||
                ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show rationale and request permissions again
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_CODE_PERMISSIONS
                )
            } else {
                // Request permissions
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_CODE_PERMISSIONS
                )
            }
        } else {
            // Permissions already granted
            Toast.makeText(this, "Permissions already granted", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // Permissions granted
                Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show()
            } else {
                // Permissions denied

            }
        }
    }

}


