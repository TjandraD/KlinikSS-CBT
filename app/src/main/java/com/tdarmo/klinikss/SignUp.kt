package com.tdarmo.klinikss

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signup.*

class SignUp : AppCompatActivity() {
    var mDatabase: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        actionBar?.hide()

        btnSignUp.setOnClickListener{
            val email: String = inputEmail.text.toString().trim()
            val password: String = inputPassword.text.toString().trim()

            if (email.isEmpty()){
                inputEmail.error = "E-mail harus diisi"
                inputEmail.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                inputEmail.error = "Format e-mail salah"
                inputEmail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty() || password.length < 6){
                inputPassword.error = "Password minimal terdiri atas 6 karakter"
                inputPassword.requestFocus()
                return@setOnClickListener
            }

            mDatabase.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) {task->
                    if(task.isSuccessful){
                        Log.d("SignUp", "createUserWithEmail:success")
                        Toast.makeText(this, "Registrasi berhasil", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@SignUp, Dashboard::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }else{
                        Log.w("SignUp", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}