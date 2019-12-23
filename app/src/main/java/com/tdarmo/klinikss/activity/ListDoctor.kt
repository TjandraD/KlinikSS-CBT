package com.tdarmo.klinikss.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.google.firebase.database.*
import com.tdarmo.klinikss.R
import com.tdarmo.klinikss.adapter.AdapterDoctor
import com.tdarmo.klinikss.models.Doctor
import kotlinx.android.synthetic.main.list_doctor.*

class ListDoctor : AppCompatActivity() {
    private lateinit var ref: DatabaseReference
    lateinit var list: MutableList<Doctor>
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_doctor)
        val actionBar = supportActionBar
        actionBar!!.title = "List Dokter"

        btnAddDoctor.setOnClickListener{
            val intent = Intent(this, AddDoctor::class.java)
            startActivity(intent)
        }

        ref = FirebaseDatabase.getInstance().getReference("Doctor")
        list = mutableListOf()
        listView = this.findViewById(R.id.listViewDoctor)

        ref.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError){

            }
            override fun onDataChange(p0: DataSnapshot){
                list.clear()
                if(p0.exists()){
                    for (h in p0.children){
                        val a = h.getValue(Doctor::class.java)
                        list.add(a!!)
                    }
                    val adapter = AdapterDoctor(
                        this@ListDoctor,
                        R.layout.doctor,
                        list
                    )
                    listView.adapter = adapter
                }
            }
        })
    }
}
