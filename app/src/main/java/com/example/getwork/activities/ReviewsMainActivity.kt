package com.example.getwork.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.getwork.databinding.ActivityMainreviewsBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ReviewsMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainreviewsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainreviewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val firebase : DatabaseReference = FirebaseDatabase.getInstance().getReference()


        binding.imageButton7.setOnClickListener {
            val intent = Intent(this, wite_a_review::class.java)
            startActivity(intent)

            Toast.makeText( this, "Email and password are required.", Toast.LENGTH_SHORT).show()

        }


        binding.imageButton8.setOnClickListener {
            val intent = Intent(this, rating_and_review::class.java)
            startActivity(intent)

        }
        binding.textView10.setOnClickListener {
            val intent = Intent(this, calculation::class.java)
            startActivity(intent)

        }

    }
}