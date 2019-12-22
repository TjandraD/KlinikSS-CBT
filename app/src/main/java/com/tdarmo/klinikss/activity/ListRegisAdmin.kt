package com.tdarmo.klinikss.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.google.firebase.database.*
import com.tdarmo.klinikss.R
import com.tdarmo.klinikss.adapter.AdapterRegisAdmin
import com.tdarmo.klinikss.models.Regist

class ListRegisAdmin : AppCompatActivity() {
    private lateinit var ref : DatabaseReference
    lateinit var list : MutableList<Regist>
    lateinit var listView : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_regis_admin)
        val actionBar = supportActionBar
        actionBar!!.title = "List Registrasi"

        ref = FirebaseDatabase.getInstance().getReference("Registration")
        list = mutableListOf()
        listView = this.findViewById(R.id.listViewRegis)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError){

            }
            override fun onDataChange(p0: DataSnapshot){
                if (p0.exists()){
                    for (h in p0.children){
                        val a = h.getValue(Regist::class.java)
                        list.add(a!!)
                    }
                    val adapter = AdapterRegisAdmin(
                        applicationContext,
                        R.layout.regist_admin,
                        list
                    )
                    listView.adapter = adapter
                }
            }
        })
    }
}
