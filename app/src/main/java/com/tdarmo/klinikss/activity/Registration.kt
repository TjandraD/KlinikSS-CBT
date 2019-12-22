package com.tdarmo.klinikss.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.tdarmo.klinikss.R
import com.tdarmo.klinikss.models.Regist
import kotlinx.android.synthetic.main.activity_registration.*
import java.text.SimpleDateFormat
import java.util.*


class Registration : AppCompatActivity() {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("Registration")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        val actionbar = supportActionBar
        actionbar!!.title = "Pendaftaran"

        val spinner: Spinner = this.findViewById(R.id.genderSpinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.genderList,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun setMode(selectedMode: Int) {
        when (selectedMode) {
            R.id.add_data -> {
                saveData()
                val intent = Intent(this, Dashboard::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
    }

    private fun saveData() {
        val name = inputName.text.toString().trim()
        val age = inputAge.text.toString().trim() + " tahun"
        val complaint = inputKeluhan.text.toString().trim()
        val email = FirebaseAuth.getInstance().currentUser?.email.toString()
        val date: String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())

        val spinner: Spinner = findViewById(R.id.genderSpinner)
        val pos = spinner.selectedItemPosition
        val gender = spinner.getItemAtPosition(pos).toString()

        val regist = Regist(name, age, gender, complaint, email, date)



        database.push().setValue(regist).addOnCompleteListener {
            Toast.makeText(this, "Pendaftaran berhasil", Toast.LENGTH_SHORT).show()
        }
    }
}