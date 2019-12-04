package com.tdarmo.klinikss

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_signup.*

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        btnSignUp.setOnClickListener{
            val name: String = inputName.text.toString()
            val address: String = inputAddress.text.toString()
            val phoneNo: String = inputPhoneNo.text.toString()
            val email: String = inputEmail.text.toString()
            val password: String = inputPassword.text.toString()

            if (name.isEmpty() || address.isEmpty() || phoneNo.isEmpty() || email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Please insert E-mail and Password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else{
                FirebaseAuth.getInstance()!!
                    .createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) {task ->
                        if(task.isSuccessful){
                            Log.d("CreateAccountActivity", "createUserWithEmail:success")
                            val userId = FirebaseAuth.getInstance().currentUser!!.uid
                            Toast.makeText(this, "Registration success", Toast.LENGTH_SHORT).show()

                            val currentUserDb = FirebaseDatabase.getInstance().reference.child("Users")
                            currentUserDb.child("name").setValue(name)
                            currentUserDb.child("address").setValue(address)
                            currentUserDb.child("phone_no").setValue(phoneNo)

                            val intent = Intent(this, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            startActivity(intent)
                        }else{
                            Log.w("CreateAccountActivity", "createUserWithEmail:failure", task.exception)
                            Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}
