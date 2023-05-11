package com.example.getwork.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.getwork.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import com.example.getwork.R

@SuppressLint("CheckResult")
class LoginActivity : AppCompatActivity() {

    private lateinit var binding:ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var ref:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

//  Auth
        auth = FirebaseAuth.getInstance()
        ref = FirebaseDatabase.getInstance("https://authenticationapp-fedcb-default-rtdb.firebaseio.com/")
            .getReference("Users")

//  Username Validation
        val usernameStream = RxTextView.textChanges(binding.etEmail)
            .skipInitialValue()
            .map {  username ->
                username.isEmpty()
            }
        usernameStream.subscribe{
            showTextMinimalAlert(it,"Email/Username")
        }

//  Password Validation
        val passwordStream = RxTextView.textChanges(binding.etPassword)
            .skipInitialValue()
            .map {  username ->
                username.isEmpty()
            }
        passwordStream.subscribe{
            showTextMinimalAlert(it,"Password")
        }

//  Button Enable True or False
        val invalidFieldStream = Observable.combineLatest(
            usernameStream,
            passwordStream,
            { usernameInvalid:Boolean , passwordInvalid:Boolean  ->
                !usernameInvalid && !passwordInvalid
            })
        invalidFieldStream.subscribe { isValid ->
            if (isValid) {
                binding.btnLogin.isEnabled= true
                binding.btnLogin.backgroundTintList = ContextCompat.getColorStateList(this,R.color.primary_color)
            }else{
                binding.btnLogin.isEnabled= false
                binding.btnLogin.backgroundTintList = ContextCompat.getColorStateList(this,android.R.color.darker_gray)
            }
        }

//  Click
        binding.btnLogin.setOnClickListener{
            //startActivity(Intent(this,HomeActivity::class.java))
            val email=binding.etEmail.text.toString().trim()
            val password=binding.etPassword.text.toString().trim()
            loginUser(email,password)
        }
        binding.tvHaventAccount.setOnClickListener{
            startActivity(Intent(this,RegisterActivity::class.java))
        }
        binding.tvForgotPw.setOnClickListener{
            startActivity(Intent(this,ResetPasswordActivity::class.java))
        }
    }

    private fun showTextMinimalAlert(isNotvalid: Boolean,text:String){
        if (text =="Email/Username")
            binding.etEmail.error= if (isNotvalid) "$text fill!" else null
        else if (text=="Password")
            binding.etPassword.error = if (isNotvalid) "$text fill!" else null
    }
    private fun loginUser(email: String ,password :String){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){ login ->
                if (login.isSuccessful){
                    val user = FirebaseAuth.getInstance().currentUser
                    val sharedPrefs = getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
                    val editor = sharedPrefs.edit()
                    editor.putString("uid", user?.uid )
                    editor.apply()

                    Intent(this,HomeActivity::class.java).also{
                        it.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                        Toast.makeText(this,"Login !",Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this,login.exception?.message,Toast.LENGTH_SHORT).show()
                }
            }
    }
}