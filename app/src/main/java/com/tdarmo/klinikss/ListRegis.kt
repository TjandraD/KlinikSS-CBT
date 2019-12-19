package com.tdarmo.klinikss

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.regis_list.*


class ListRegis : AppCompatActivity() {

    lateinit var ref : DatabaseReference
    lateinit var list : MutableList<Regist>
    lateinit var listView : ListView

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.regis_list)

        regist.setOnClickListener{
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
        }

        ref = FirebaseDatabase.getInstance().getReference("Registration")
        list = mutableListOf()
        listView = findViewById(R.id.listViewRegis)

        ref.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError){

            }
            override fun onDataChange(p0: DataSnapshot){
                if (p0!!.exists()){
                    for (h in p0.children){
                        val a = h.getValue(Regist::class.java)
                        list.add(a!!)
                    }
                    val adapter = AdapterRegis(applicationContext, R.layout.regist,list)
                    listView.adapter = adapter
                }
            }
        })
    }
}