package com.tdarmo.klinikss.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.tdarmo.klinikss.R
import com.tdarmo.klinikss.models.Doctor
import com.tdarmo.klinikss.models.Regist
import kotlinx.android.synthetic.main.edit_regis.*
import java.text.SimpleDateFormat
import java.util.*

class EditRegis : AppCompatActivity() {
    var spinnerDataList = arrayListOf<String>()
    var adapter: ArrayAdapter<String>? = null
    private lateinit var ref: DatabaseReference
    val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("Registration")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_regis)
        val actionbar = supportActionBar
        actionbar!!.title = "Sunting Pendaftaran"

        val name = intent.getStringExtra(EXTRA_NAME)
        val age = intent.getStringExtra(EXTRA_AGE)
        val complaint = intent.getStringExtra(EXTRA_COMPLAINT)
        inputName.setText(name)
        inputAge.setText(age.take(2))
        inputComplaint.setText(complaint)

        ref = FirebaseDatabase.getInstance().getReference("Doctor")
        retrieveData()

        val spinnerDoctor: Spinner = this.findViewById(R.id.doctorSpinner)
        adapter = ArrayAdapter(this, R.layout.spinner_design, spinnerDataList)
        spinnerDoctor.adapter = adapter

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

    private fun retrieveData() {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    for (h in p0.children) {
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

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_AGE = "extra_age"
        const val EXTRA_COMPLAINT = "extra_complaint"
        const val EXTRA_ID = "extra_id"
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

        val id = intent.getStringExtra(EXTRA_ID)
        val regist = Regist(id, name, age, gender, doctor, complaint, email, date)

        database.child(id).setValue(regist).addOnCompleteListener {
            Toast.makeText(this, "Data berhasil diubah", Toast.LENGTH_SHORT).show()
        }
    }
}