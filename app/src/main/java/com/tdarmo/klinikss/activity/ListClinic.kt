package com.tdarmo.klinikss.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.google.firebase.database.*
import com.tdarmo.klinikss.adapter.AdapterClinic
import com.tdarmo.klinikss.models.Clinic
import com.tdarmo.klinikss.R
import kotlinx.android.synthetic.main.list_clinic.*

class ListClinic : AppCompatActivity() {

    lateinit var ref: DatabaseReference
    lateinit var list: MutableList<Clinic>
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_clinic)
        val actionBar = supportActionBar
        actionBar!!.title = "List Klinik"

        btnAddClinic.setOnClickListener{
            val intent = Intent(this, AddClinic::class.java)
            startActivity(intent)
        }

        ref = FirebaseDatabase.getInstance().getReference("Clinic")
        list = mutableListOf()
        listView = findViewById(R.id.listViewClinic)

        ref.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError){

            }
            override fun onDataChange(p0: DataSnapshot){
                if(p0.exists()){
                    list.clear()
                    for (h in p0.children){
                        val a = h.getValue(Clinic::class.java)
                        list.add(a!!)
                    }
                    val adapter = AdapterClinic(
                        this@ListClinic,
                        R.layout.clinic,
                        list
                    )
                    listView.adapter = adapter
                }
            }
        })
    }
}