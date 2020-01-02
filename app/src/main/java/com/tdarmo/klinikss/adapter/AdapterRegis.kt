package com.tdarmo.klinikss.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.tdarmo.klinikss.R
import com.tdarmo.klinikss.activity.Dashboard
import com.tdarmo.klinikss.activity.EditRegis
import com.tdarmo.klinikss.activity.ListRegis
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

        val update = view.findViewById<Button>(R.id.edtRegis)
        val delete = view.findViewById<Button>(R.id.dltRegis)

        val regist = list[position]

        textName.text = regist.Name
        textAge.text = regist.Age
        textGender.text = regist.Gender
        textDoctor.text = regist.Doctor
        textComplaint.text = regist.Complaint
        textDate.text = regist.Date
        val email = regist.Email
        val id = regist.id

        update.setOnClickListener{
            val intent = Intent(mCtx, EditRegis::class.java)
            intent.putExtra(EditRegis.EXTRA_NAME, list[position].Name)
            intent.putExtra(EditRegis.EXTRA_AGE, list[position].Age)
            intent.putExtra(EditRegis.EXTRA_COMPLAINT, list[position].Complaint)
            intent.putExtra(EditRegis.EXTRA_ID, list[position].id)
            mCtx.startActivity(intent)
        }
        delete.setOnClickListener{
            deleteInfo(regist)
        }

        return view
    }

    private fun deleteInfo(regist: Regist) {
        val mydatabase = FirebaseDatabase.getInstance().getReference("Registration")
        mydatabase.child(regist.id).removeValue()
        Toast.makeText(mCtx,"Dihapus!",Toast.LENGTH_SHORT).show()
        val intent = Intent(context, ListRegis::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}