package com.tdarmo.klinikss.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.tdarmo.klinikss.R
import com.tdarmo.klinikss.prevalent.Prevalent
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val refDoctor = FirebaseDatabase.getInstance().getReference("Doctor")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        btnLogin.setOnClickListener {
            val email: String = inputEmail.text.toString().trim()
            val password: String = inputPassword.text.toString().trim()
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
                        when {
                            email == "admin@example.com" -> {
                                val intent = Intent(this, AdminDashboard::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                            }
                            else -> {
                                val intent = Intent(this, Dashboard::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                            }
                        }
                    }
                }
                .addOnFailureListener{
                    refDoctor.addListenerForSingleValueEvent(object: ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {

                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            if(p0.child(email).exists() and (p0.child(email).child("Password").value == password)){
                                val name = p0.child(email).child("Name").value.toString()
                                Paper.book().write(Prevalent.doctorName, name)

                                val intent = Intent(this@LoginActivity, DoctorDashboard::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                            }
                            else if(p0.child(email).exists() and (p0.child(email).child("Password").value != password)){
                                Toast.makeText(this@LoginActivity, "Password Anda salah, mohon coba lagi", Toast.LENGTH_SHORT).show()
                            }
                            else{
                                Toast.makeText(this@LoginActivity, "Account with this email $email doesn't exist, please register first", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })

//                    Log.d("Main", "Gagal masuk: ${it.message}")
//                    Toast.makeText(this, "E-mail/Password salah", Toast.LENGTH_SHORT).show()
                }
        }

        signUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
    }
}
