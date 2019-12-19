package com.tdarmo.klinikss

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.add_clinic.*

class AddClinic : AppCompatActivity() {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("Clinic")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_clinic)
        val actionbar = supportActionBar
        actionbar!!.title = "Daftar Klinik"
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

    private fun saveData(){
        val name: String = inputName.text.toString().trim()
        val room: String = inputRoom.text.toString().trim()

        val clinic = Clinic(name, room)
        database.push().setValue(clinic).addOnCompleteListener{
            Toast.makeText(this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show()
        }
    }
}
