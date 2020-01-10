package com.tdarmo.klinikss.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.database.*
import com.tdarmo.klinikss.R
import com.tdarmo.klinikss.models.Clinic
import com.tdarmo.klinikss.models.Doctor
import kotlinx.android.synthetic.main.edit_doctor.*

class EditDoctor : AppCompatActivity() {
    var spinnerDataList = arrayListOf<String>()
    var adapter: ArrayAdapter<String>? = null
    private lateinit var ref: DatabaseReference
    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("Doctor")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_doctor)
        supportActionBar!!.title = "Sunting Dokter"

        val inputName: EditText = findViewById(R.id.inputName)
        val inputEmail: EditText = findViewById(R.id.inputEmail)
        val inputPassword: EditText = findViewById(R.id.inputPassword)

        val name = intent.getStringExtra(EXTRA_NAME)
        val email = intent.getStringExtra(EXTRA_EMAIL)
        val password = intent.getStringExtra(EXTRA_PASSWORD)
        inputName.setText(name)
        inputEmail.setText(email)
        inputPassword.setText(password)

        ref = FirebaseDatabase.getInstance().getReference("Clinic")
        retrieveData()
    }

    companion object{
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_PASSWORD = "extra_password"
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

        val spinner: Spinner = findViewById(R.id.clinicSpinner)
        adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinnerDataList)
        spinner.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun setMode(selectedMode: Int) {
        when (selectedMode) {
            R.id.edit_data -> {
                saveData()
                val intent = Intent(this, ListDoctor::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
    }

    private fun saveData() {
        val name: String = inputName.text.toString().trim()
        val email: String = inputEmail.text.toString().trim()
        val password: String = inputPassword.text.toString().trim()
        val spinner: Spinner = findViewById(R.id.clinicSpinner)
        val pos = spinner.selectedItemPosition
        val clinic = spinner.getItemAtPosition(pos).toString()

        val doctorId = intent.getStringExtra(EXTRA_ID)

        val doctor = Doctor(doctorId, name, clinic, email, password)
        database.child(doctorId).setValue(doctor).addOnCompleteListener{
            Toast.makeText(this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show()
        }
    }
}
