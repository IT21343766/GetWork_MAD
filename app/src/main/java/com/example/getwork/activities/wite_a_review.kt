package com.example.incomingcall

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class wite_a_review : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etReviews: EditText
    private lateinit var btnSaveChanges: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wite_a_review)


        etName = findViewById(R.id.editTextTextPersonName6)
        etReviews = findViewById(R.id.editTextTextPersonName2)
        btnSaveChanges = findViewById(R.id.button3)

        dbRef = FirebaseDatabase.getInstance().getReference("Reviews")
        //getInstance().getReference("Reviews")

        btnSaveChanges.setOnClickListener {
            saveName()

        }


    }
    private fun saveName(){
        //getting values
        val Name = etName.text.toString()
        val Reviews = etName.text.toString()

        if(Name.isEmpty()){
            etName.error = "Please enter a name"
        }
        if(Reviews.isEmpty()){
            etReviews.error = "Please enter a Review"
        }

        val Id = dbRef.push().key!!

        val review = Reviews(Name, Reviews)

        dbRef.child(Id).setValue(review)
            .addOnCompleteListener{
                Toast.makeText(this,"Data insert Successfully", Toast.LENGTH_LONG).show()
            }.addOnFailureListener{ err->
                Toast.makeText(this,"Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }

}