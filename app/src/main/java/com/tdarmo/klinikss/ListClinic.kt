package com.tdarmo.klinikss

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.list_clinic.*

class ListClinic : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_clinic)
        val actionBar = supportActionBar
        actionBar!!.title = "List Klinik"

        btnAddClinic.setOnClickListener{
            val intent = Intent(this, AddClinic::class.java)
            startActivity(intent)
        }
    }
}