package com.tdarmo.klinikss

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_registration.*

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
                finish()
            }
        }
    }

    private fun saveData() {
        val name = inputName.text.toString().trim()
        val age = inputAge.text.toString().trim()
        val complaint = inputKeluhan.text.toString().trim()

        val spinner: Spinner = findViewById(R.id.genderSpinner)
        val pos = spinner.selectedItemPosition
        val gender = spinner.getItemAtPosition(pos).toString()

        val regist = Regist(name, age, gender, complaint)

        database.push().setValue(regist).addOnCompleteListener {
            Toast.makeText(this, "Pendaftaran berhasil", Toast.LENGTH_SHORT).show()
        }
    }
}