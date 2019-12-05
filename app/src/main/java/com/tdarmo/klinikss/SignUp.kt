package com.tdarmo.klinikss

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_signup.*

class SignUp : AppCompatActivity() {
    var mDatabase: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        btnSignUp.setOnClickListener{
            val email: String = inputEmail.text.toString()
            val password: String = inputPassword.text.toString()

            mDatabase.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) {task->
                    if(task.isSuccessful){
                        Log.d("SignUp", "createUserWithEmail:success")
                        Toast.makeText(this, "Registrasi berhasil", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }else{
                        Log.w("SignUp", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}