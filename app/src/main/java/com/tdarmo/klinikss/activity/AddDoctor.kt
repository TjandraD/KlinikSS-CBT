package com.tdarmo.klinikss.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.database.*
import com.tdarmo.klinikss.R
import com.tdarmo.klinikss.models.Clinic
import com.tdarmo.klinikss.models.Doctor
import kotlinx.android.synthetic.main.add_doctor.*

class AddDoctor : AppCompatActivity() {
    var spinnerDataList = arrayListOf<String>()
    var adapter: ArrayAdapter<String>? = null
    private lateinit var ref: DatabaseReference
    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("Doctor")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_doctor)
        val actionbar = supportActionBar
        actionbar!!.title = "Daftar Dokter"

        ref = FirebaseDatabase.getInstance().getReference("Clinic")
        retrieveData()

        val spinner: Spinner = this.findViewById(R.id.clinicSpinner)
        adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinnerDataList)
        spinner.adapter = adapter
    }

    private fun retrieveData(){
        ref.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError){

            }
            override fun onDataChange(p0: DataSnapshot){
                if(p0.exists()){
                    for(h in p0.children){
                        val a = h.getValue(Clinic::class.java)
                        if (a != null) {
                            spinnerDataList.add(a.Name)
                        }
                    }
                }
                adapter?.notifyDataSetChanged()
            }
        })
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
                val intent = Intent(this, AdminDashboard::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
    }

    private fun saveData(){
        val name: String = inputName.text.toString().trim()
        val spinner: Spinner = findViewById(R.id.clinicSpinner)
        val pos = spinner.selectedItemPosition
        val clinic = spinner.getItemAtPosition(pos).toString()

        val doctor = Doctor(name, clinic)
        database.push().setValue(doctor).addOnCompleteListener{
            Toast.makeText(this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show()
        }
    }
}
