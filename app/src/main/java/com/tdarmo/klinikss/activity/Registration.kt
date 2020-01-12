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
import com.google.firebase.database.*
import com.tdarmo.klinikss.R
import com.tdarmo.klinikss.models.Doctor
import com.tdarmo.klinikss.models.Regist
import kotlinx.android.synthetic.main.activity_registration.*
import java.text.SimpleDateFormat
import java.util.*


class Registration : AppCompatActivity() {
    var spinnerDataList = arrayListOf<String>()
    var adapter: ArrayAdapter<String>? = null
    private lateinit var ref: DatabaseReference
    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("Registration")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        val actionbar = supportActionBar
        actionbar!!.title = "Pendaftaran"

        ref = FirebaseDatabase.getInstance().getReference("Doctor")
        retrieveData()

        val spinnerDoctor: Spinner = this.findViewById(R.id.doctorSpinner)
        adapter = ArrayAdapter(this, R.layout.spinner_design, spinnerDataList)
        spinnerDoctor.adapter = adapter

        val spinner: Spinner = this.findViewById(R.id.genderSpinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.genderList,
            R.layout.spinner_design
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_design)
            spinner.adapter = adapter
        }
    }

    private fun retrieveData(){
        ref.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError){

            }
            override fun onDataChange(p0: DataSnapshot){
                if(p0.exists()){
                    for(h in p0.children){
                        val a = h.getValue(Doctor::class.java)
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
                val intent = Intent(this, ListRegis::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
    }

    private fun saveData() {
        val name = inputName.text.toString().trim()
        val age = inputAge.text.toString().trim() + " tahun"
        val complaint = inputComplaint.text.toString().trim()
        val email = FirebaseAuth.getInstance().currentUser?.email.toString()
        val date: String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())

        val spinner: Spinner = findViewById(R.id.genderSpinner)
        val pos = spinner.selectedItemPosition
        val gender = spinner.getItemAtPosition(pos).toString()

        val spinnerDoctor: Spinner = findViewById(R.id.doctorSpinner)
        val posSD = spinnerDoctor.selectedItemPosition
        val doctor = spinnerDoctor.getItemAtPosition(posSD).toString()

        val id = database.push().key.toString()
        val regist = Regist(id, name, age, gender, doctor, complaint, email, date)

        database.child(id).setValue(regist).addOnCompleteListener {
            Toast.makeText(this, "Pendaftaran berhasil", Toast.LENGTH_SHORT).show()
        }
    }
}