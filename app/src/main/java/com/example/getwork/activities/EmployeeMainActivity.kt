package com.example.eduxo
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EmployeeMainActivity : AppCompatActivity() {

    private lateinit var btnInsertData: Button

    private lateinit var btnFetchData: Button

    private lateinit var btnLoginData: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employeemain)

        val firebase : DatabaseReference = FirebaseDatabase.getInstance().getReference()


        btnInsertData = findViewById(R.id.btnInsertData)
       // btnFetchData = findViewById(R.id.btnFetchData)
        btnLoginData = findViewById(R.id.btnLoginData)


        btnInsertData.setOnClickListener {
            val intent = Intent(this, EmployeeRegisterActivity::class.java)
            startActivity(intent)
        }

       /* btnFetchData.setOnClickListener {
            val intent = Intent(this, FetchingActivity::class.java)
            startActivity(intent)
        }*/

        btnLoginData.setOnClickListener {
            val intent = Intent(this, EmployeeLoginActivity::class.java)
            startActivity(intent)
        }

    }
}