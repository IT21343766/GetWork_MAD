package com.example.eduxo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*


class ProfileActivity : AppCompatActivity() {

    private lateinit var empRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var empList: ArrayList<UserModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_user)

        empRecyclerView = findViewById(R.id.rvEmp)
        empRecyclerView.layoutManager = LinearLayoutManager(this)
        empRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        empList = arrayListOf<UserModel>()

        getEmployeesData()

    }

    private fun getEmployeesData() {

        empRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                empList.clear()
                if (snapshot.exists()){
                    for (empSnap in snapshot.children){
                        val empData = empSnap.getValue(UserModel::class.java)
                        EmployeeLoginActivity.checkedUser()
                        if (empData != null) {
                            if(empData.empUserName == EmployeeLoginActivity.checkedUser().split("|")[0] && empData.emppassword == EmployeeLoginActivity.checkedUser().split("|")[1]){
                                empList.add(empData!!)
                            }
                        }
                    }
                    val mAdapter = EmpAdapter(empList)
                    empRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : EmpAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@ProfileActivity, UserDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("empId", empList[position].empId)
                            intent.putExtra("empName", empList[position].empName)
                            intent.putExtra("empAge", empList[position].empAge)
                            intent.putExtra("empPhoneNo", empList[position].empPhoneNo)
                            intent.putExtra("empAddress", empList[position].empAddress)
                            intent.putExtra("empUserName", empList[position].empUserName)
                            intent.putExtra("emppassword", empList[position].emppassword)
                            intent.putExtra("empJobtitle", empList[position].empJobtitle)


                            startActivity(intent)
                        }

                    })

                    empRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}