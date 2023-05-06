package com.example.getwork.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.getwork.databinding.ActivityPaymentDashboardBinding

class PaymentsDashboard : AppCompatActivity() {

    private lateinit var binding:ActivityPaymentDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonProceedToPay.setOnClickListener{
            var intent = Intent(this, PaymentConfirmActivity::class.java)
            startActivity(intent)
        }

        binding.buttonPaymentHistory.setOnClickListener{
            var intent = Intent(this, PaymentHistoryActivity::class.java)
            startActivity(intent)
        }

        binding.buttonEnterPaymentInformation.setOnClickListener{
            var intent = Intent(this, ReadPaymentInformationActivity::class.java)
            startActivity(intent)
        }
    }
}