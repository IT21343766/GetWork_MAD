package com.example.getwork.activities

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.getwork.databinding.ActivityCreatePaymentInformationBinding
import com.example.getwork.models.EmployeePaymentInfoModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreatePaymentInformationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreatePaymentInformationBinding
    private lateinit var fbdbref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePaymentInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPayInfoSubmit.setOnClickListener {
            handleSubmit()
        }
        supportActionBar?.title = "Add Payment Information"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    //handles validations and continues to submission
    private fun handleSubmit() {
        // validations for account holders name
        if (binding.editTextAccountHoldersName.text.toString().isNullOrEmpty()) {
            binding.editTextAccountHoldersName.error = "This field is required"
        }

        // validations for account number field
        else if (binding.editTextAccountNo.text.toString().isNullOrEmpty()) {
            binding.editTextAccountNo.error = "This field is required"
        }

        // checking if account number is all numeric using regex expression
        else if (!(Regex("[0-9]+") matches ((binding.editTextAccountNo.text.toString())))) {
            binding.editTextAccountNo.error = "This field cnnot contain letters"
        }


        // validations for bank name
        else if (binding.editTextBankName.text.toString().isNullOrEmpty()) {
            binding.editTextBankName.error = "This field is required"
        }

        // validations for bank branch name
        else if (binding.editTextBankBranchName.text.toString().isNullOrEmpty()) {
            binding.editTextBankBranchName.error = "This field is required"
        }
        // if all checks out continue to submission
        else {
            submitPaymentInformation()
        }
    }

    private fun submitPaymentInformation() {
        binding.btnPayInfoSubmit.isEnabled = false
        binding.btnPayInfoSubmit.text = "Uploading..."

        fbdbref = FirebaseDatabase
            .getInstance("https://getworkdb-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("PaymentInformation")
        val loc: String = fbdbref.push().key!!
        val sharedPrefs = getSharedPreferences("userPrefs", Context.MODE_PRIVATE)

        val accHoldersName = binding.editTextAccountHoldersName.text.toString()
        val accNo = binding.editTextAccountNo.text.toString()
        val bankName = binding.editTextBankName.text.toString()
        val bankBranchName = binding.editTextBankBranchName.text.toString()

        val empPayInfo = EmployeePaymentInfoModel(
            sharedPrefs.getString("uid", null).toString(),
            accHoldersName,
            accNo,
            bankName,
            bankBranchName
        )

        fbdbref.child(loc).setValue(empPayInfo)
            // handle if payment info not provided yet
            .addOnCompleteListener {
                Toast.makeText(this, "Payment Info added", Toast.LENGTH_LONG).show()
                finish()
            }.addOnFailureListener { err ->
                Toast.makeText(
                    this,
                    "Payment info upload failed : ${err.message}",
                    Toast.LENGTH_LONG
                )
                    .show()

                // end activity on completion
                finish()
            }
    }
}