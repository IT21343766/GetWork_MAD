package com.example.getwork.activities

import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.getwork.adapters.PaymentHistoryAdapter
import com.example.getwork.databinding.ActivityPaymentHistoryBinding
import com.example.getwork.models.PaymentModel
import com.google.firebase.database.*

class PaymentHistoryActivity: AppCompatActivity() {
    private lateinit var binding: ActivityPaymentHistoryBinding
    private lateinit var payHistList: ArrayList<PaymentModel>
    private lateinit var firebaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "My Payment History"

        payHistList = arrayListOf<PaymentModel>()
        getPaymentHistory()
    }

    @Suppress("DEPRECATION")
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun getPaymentHistory() {
        binding.recyclerPaymentHistory.visibility = View.GONE
        binding.paymentHistoryLoadingMessage.visibility = View.VISIBLE
        firebaseRef = FirebaseDatabase
            .getInstance("https://getworkdb-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("Payments")
        val sharedPref = getSharedPreferences("userPrefs", MODE_PRIVATE)

        val query = firebaseRef.orderByChild("uid")
            .equalTo(sharedPref.getString("uid", null))

        query.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                payHistList.clear()
                if(snapshot.exists()) {
                  for(payHistSnap in snapshot.children) {
                      val payHistData = payHistSnap.getValue(PaymentModel::class.java)
                      payHistList.add(payHistData!!)
                  }

                    val mAdapter = PaymentHistoryAdapter(payHistList)
                    binding.recyclerPaymentHistory.layoutManager = LinearLayoutManager(this@PaymentHistoryActivity)
                    binding.recyclerPaymentHistory.adapter = mAdapter

                    binding.recyclerPaymentHistory.visibility = View.VISIBLE
                    binding.paymentHistoryLoadingMessage.visibility = View.GONE
                }
                else {
                    binding.paymentHistoryLoadingMessage.text = "No payments made yet."
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@PaymentHistoryActivity, "Fetch cancelled: ${error.message}", Toast.LENGTH_LONG).show()
            }

        })
    }
}