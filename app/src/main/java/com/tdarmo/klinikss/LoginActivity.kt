package com.tdarmo.klinikss

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        getSupportActionBar()?.hide()

        btnLogin.setOnClickListener {
            val email: String = inputEmail.text.toString()
            val password: String = inputPassword.text.toString()
            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Mohon masukkan E-mail dan Password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                .addOnCompleteListener{
                    if(!it.isSuccessful) {
                        return@addOnCompleteListener
                    }
                    else {
                        Toast.makeText(this, "Selamat datang", Toast.LENGTH_SHORT).show()
                        if(email == "admin@example.com" && password == "admin123"){
                            val intent = Intent(this, AdminDashboard::class.java).apply { Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK }
                            startActivity(intent)
                        }else{
                            val intent = Intent(this, Dashboard::class.java).apply { Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK }
                            startActivity(intent)
                        }
                    }
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
