package com.tdarmo.klinikss.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.tdarmo.klinikss.models.Clinic
import com.tdarmo.klinikss.R

class AdapterClinic(val mCtx: Context, val layoutResId: Int, val list: List<Clinic>): ArrayAdapter<Clinic>(mCtx,layoutResId,list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View{
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId,null)

        val textName = view.findViewById<TextView>(R.id.textName)
        val textRoom = view.findViewById<TextView>(R.id.textRoom)

        val clinic = list[position]

        textName.text = clinic.Name
        textRoom.text = clinic.Room

        return view
    }
}