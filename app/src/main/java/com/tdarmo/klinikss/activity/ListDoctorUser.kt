package com.tdarmo.klinikss.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.google.firebase.database.*
import com.tdarmo.klinikss.R
import com.tdarmo.klinikss.adapter.AdapterDoctorUser
import com.tdarmo.klinikss.models.Doctor

class ListDoctorUser : AppCompatActivity() {
    private lateinit var ref: DatabaseReference
    lateinit var list: MutableList<Doctor>
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_doctor_user)
        val actionBar = supportActionBar
        actionBar!!.title = "List Dokter"

        ref = FirebaseDatabase.getInstance().getReference("Doctor")
        list = mutableListOf()
        listView = this.findViewById(R.id.listViewDoctor)

        ref.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError){

            }
            override fun onDataChange(p0: DataSnapshot){
                if(p0.exists()){
                    for (h in p0.children){
                        val a = h.getValue(Doctor::class.java)
                        list.add(a!!)
                    }
                    val adapter = AdapterDoctorUser(
                        applicationContext,
                        R.layout.doctor_user,
                        list
                    )
                    listView.adapter = adapter
                }
            }
        })
    }
}
