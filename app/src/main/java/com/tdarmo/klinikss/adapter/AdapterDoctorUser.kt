package com.tdarmo.klinikss.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.tdarmo.klinikss.R
import com.tdarmo.klinikss.models.Doctor

class AdapterDoctorUser(val mCtx: Context, val layoutResId: Int, val list: List<Doctor>): ArrayAdapter<Doctor>(mCtx, layoutResId, list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId,null)

        val textName = view.findViewById<TextView>(R.id.textName)
        val textClinic = view.findViewById<TextView>(R.id.textClinic)

        val doctor = list[position]

        textName.text = doctor.Name
        textClinic.text = doctor.Clinic

        return view
    }
}