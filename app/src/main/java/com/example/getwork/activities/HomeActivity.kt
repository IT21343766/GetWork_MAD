package com.example.getwork.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.getwork.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    private  lateinit var  binding:ActivityHomeBinding
    private lateinit var  auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth =FirebaseAuth.getInstance()

        binding.btnLogout.setOnClickListener{
            startActivity(Intent(this,LoginActivity::class.java))
        }
        binding.btnUser.setOnClickListener{
            val sharedPrefs = getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
            val editor = sharedPrefs.edit()
            editor.clear().apply()
            startActivity(Intent(this,UserHomeActivity::class.java))
        }
        binding.btnPayments.setOnClickListener{
            startActivity(Intent(this,PaymentDashboard::class.java))
        }
    }

}