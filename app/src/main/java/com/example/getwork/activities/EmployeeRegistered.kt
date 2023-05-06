package com.example.eduxo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EmployeeRegistered : AppCompatActivity() {

    private lateinit var btbackData: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.employeeregistered)

        val firebase : DatabaseReference = FirebaseDatabase.getInstance().getReference()


        btbackData = findViewById(R.id.btbackData)

        btbackData.setOnClickListener {
            val intent = Intent(this, EmployeeMainActivity::class.java)
            startActivity(intent)
        }

    }

}