package com.tdarmo.klinikss.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.tdarmo.klinikss.adapter.AdapterRegis
import com.tdarmo.klinikss.R
import com.tdarmo.klinikss.models.Regist
import kotlinx.android.synthetic.main.regis_list.*

class ListRegis : AppCompatActivity() {

    private lateinit var ref : DatabaseReference
    lateinit var list : MutableList<Regist>
    lateinit var listView : ListView

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.regis_list)
        val actionBar = supportActionBar
        actionBar!!.title = "List Registrasi"

        regist.setOnClickListener{
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
        }

        ref = FirebaseDatabase.getInstance().getReference("Registration")
        list = mutableListOf()
        listView = this.findViewById(R.id.listViewRegis)

        ref.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError){

            }
            override fun onDataChange(p0: DataSnapshot){
                list.clear()
                if (p0.exists()){
                    for (h in p0.children){
                        val a = h.getValue(Regist::class.java)
                        val email = FirebaseAuth.getInstance().currentUser?.email.toString()
                        if (a != null) {
                            if (a.Email == email){
                                list.add(a)
                            }
                        }
                    }
                    val adapter = AdapterRegis(
                        this@ListRegis,
                        R.layout.regist,
                        list
                    )
                    listView.adapter = adapter
                }
            }
        })
    }

    override fun onBackPressed(){
        val intent = Intent(this, Dashboard::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}