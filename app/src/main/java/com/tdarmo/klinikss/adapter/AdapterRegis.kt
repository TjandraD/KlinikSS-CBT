package com.tdarmo.klinikss.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.tdarmo.klinikss.R
import com.tdarmo.klinikss.models.Regist

class AdapterRegis(val mCtx: Context, val layoutResId: Int, val list: List<Regist>): ArrayAdapter<Regist>(mCtx,layoutResId,list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View{
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId,null)

        val textName = view.findViewById<TextView>(R.id.textName)
        val textAge = view.findViewById<TextView>(R.id.textAge)
        val textGender = view.findViewById<TextView>(R.id.textGender)
        val textDoctor = view.findViewById<TextView>(R.id.textDoctor)
        val textComplaint = view.findViewById<TextView>(R.id.textComplaint)
        val textDate = view.findViewById<TextView>(R.id.textDate)

        val regist = list[position]

        textName.text = regist.Name
        textAge.text = regist.Age
        textGender.text = regist.Gender
        textDoctor.text = regist.Doctor
        textComplaint.text = regist.Complaint
        textDate.text = regist.Date

        return view
    }
}