package com.example.getwork.activities

import android.content.Intent
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
            startActivity(Intent(this,UserHomeActivity::class.java))
        }
    }

}