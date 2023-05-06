package com.example.incomingcall

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.incomingcall.databinding.RatingAndReviewBinding

class rating_and_review : AppCompatActivity() {
    private lateinit var binding: RatingAndReviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rating_and_review)

        binding = RatingAndReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imageButton4.setOnClickListener {
            val intent= Intent(this,rating_and_review::class.java)
            startActivity(intent)
        }
    }
}