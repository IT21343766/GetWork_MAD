package com.example.eduxo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EmployeeRegisterActivity : AppCompatActivity() {

    private lateinit var etEmpName: EditText
    private lateinit var etEmpAge: EditText
    private lateinit var etEmpPhoneNo: EditText
    private lateinit var etEmpAddress: EditText
    private lateinit var etEmpUserName: EditText
    private lateinit var etEmppassword: EditText
    private lateinit var etEmpJobtitle: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employeeregister)

        etEmpName = findViewById(R.id.etEmpName)
        etEmpAge = findViewById(R.id.etEmpAge)
        etEmpPhoneNo = findViewById(R.id.etEmpPhoneNo)
        etEmpAddress = findViewById(R.id.etEmpAddress)
        etEmpUserName = findViewById(R.id.etEmpUserName)
        etEmppassword = findViewById(R.id.etEmppassword)
        etEmpJobtitle = findViewById(R.id.etEmpJobtitle)
        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        btnSaveData.setOnClickListener {
            saveEmployeeData()
        }
    }

    private fun saveEmployeeData() {

        //getting values
        val empName = etEmpName.text.toString()
        val empAge = etEmpAge.text.toString()
        val empPhoneNo = etEmpPhoneNo.text.toString()
        val empAddress = etEmpAddress.text.toString()
        val empUserName = etEmpUserName.text.toString()
        val emppassword = etEmppassword.text.toString()
        val empJobtitle = etEmpJobtitle.text.toString()

        if (empName.isEmpty()) {
            etEmpName.error = "Please enter name"
        }
        if (empAge.isEmpty()) {
            etEmpAge.error = "Please enter age"
        }
        if (empPhoneNo.isEmpty()) {
            etEmpPhoneNo.error = "Please enter Phone Number"
        }
        if (empAddress.isEmpty()) {
            etEmpAddress.error = "Please enter Address"
        }
        if (empUserName.isEmpty()) {
            etEmpUserName.error = "Please enter UserName"
        }
        if (emppassword.isEmpty()) {
            etEmppassword.error = "Please enter Password"
        }
        if (empJobtitle.isEmpty()) {
            etEmpJobtitle.error = "Please enter Job Title"
        }

        val empId = dbRef.push().key!!

        val employee = UserModel(empId, empName, empAge, empPhoneNo, empAddress, empUserName, emppassword, empJobtitle)

        dbRef.child(empId).setValue(employee)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                etEmpName.text.clear()
                etEmpAge.text.clear()
                etEmpPhoneNo.text.clear()
                etEmpAddress.text.clear()
                etEmpUserName.text.clear()
                etEmppassword.text.clear()
                etEmpJobtitle.text.clear()

                val intent = Intent(this, EmployeeRegistered::class.java)
                startActivity(intent)


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }

}