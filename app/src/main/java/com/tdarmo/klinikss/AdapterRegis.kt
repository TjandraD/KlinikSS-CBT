package com.tdarmo.klinikss

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class AdapterRegis(val mCtx: Context, val layoutResId: Int, val list: List<Regist>): ArrayAdapter<Regist>(mCtx,layoutResId,list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View{
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId,null)

        val textName = view.findViewById<TextView>(R.id.textName)
        val textAge = view.findViewById<TextView>(R.id.textAge)
        val textGender = view.findViewById<TextView>(R.id.textGender)
        val textComplaint = view.findViewById<TextView>(R.id.textComplaint)

        val regist = list[position]

        textName.text = regist.Name
        textAge.text = regist.Age
        textGender.text = regist.Gender
        textComplaint.text = regist.Complaint

        return view
    }
}