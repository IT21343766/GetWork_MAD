package com.example.eduxo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class EmployeeLoginActivity : AppCompatActivity() {


    private lateinit var etUserName: EditText
    private lateinit var etPassword: EditText

    private lateinit var btnLogin: Button
    private lateinit var dbRef: DatabaseReference
    public lateinit var empList: ArrayList<UserModel>
    private var  check :Boolean = false



    companion object{
        public var  correctUser : String  = ""
        public var  correctPassword : String  = ""
        fun checkedUser(): String {
            return correctUser+ "|" + correctPassword
        }
    }

    //@SuppressLint("MissingInflatedId")
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        etUserName = findViewById(R.id.etUserName)
        etPassword = findViewById(R.id.etPassword)

        btnLogin = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        btnLogin.setOnClickListener {
            getEmployeesData()
        }
    }

    private fun comfireValues(
        userName: String,
        empUserName: String?,
        password: String,
        emppassword: String?,
        empData: UserModel
    ) {
        if (empUserName != null) {

            val user :String
            val pass :String
            user = userName.compareTo(empUserName, false).toString()
            pass = password.compareTo(emppassword.toString(),false).toString()

            if (user == "0" && pass == "0"){
                check =true
                correctUser = userName
                correctPassword = password
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
            }
        }
    }
    private fun getEmployeesData() {

        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (empSnap in snapshot.children){
                    val empData = empSnap.getValue(UserModel::class.java)

                    if (empData != null) {
                        comfireValues(etUserName.text.toString(),empData.empUserName, etPassword.text.toString(), empData.emppassword, empData)
                    }
                }
                if (!check){
                    etUserName.error ="Wrong Username of Password"
                    etPassword.error ="Wrong Username of Password"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}