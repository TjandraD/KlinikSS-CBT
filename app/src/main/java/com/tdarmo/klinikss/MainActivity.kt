package com.tdarmo.klinikss

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getSupportActionBar()?.hide()

        btnLogin.setOnClickListener {
            val email: String = inputEmail.text.toString()
            val password: String = inputPassword.text.toString()
            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Please insert E-mail and Password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                .addOnCompleteListener{
                    if(!it.isSuccessful) {
                        return@addOnCompleteListener
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                    else
                        Toast.makeText(this, "Selamat datang", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, Dashboard::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener{
                    Log.d("Main", "Gagal masuk: ${it.message}")
                    Toast.makeText(this, "E-mail/Password salah", Toast.LENGTH_SHORT).show()
                }
        }

        signUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
    }
}
