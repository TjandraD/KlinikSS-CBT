package com.tdarmo.klinikss.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.*
import com.tdarmo.klinikss.R
import com.tdarmo.klinikss.activity.AdminDashboard
import com.tdarmo.klinikss.activity.EditDoctor
import com.tdarmo.klinikss.activity.ListDoctor
import com.tdarmo.klinikss.models.Doctor

class AdapterDoctor(val mCtx: Context, val layoutResId: Int, val list: List<Doctor>): ArrayAdapter<Doctor>(mCtx, layoutResId, list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View{
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId,null)

        val textName = view.findViewById<TextView>(R.id.textName)
        val textClinic = view.findViewById<TextView>(R.id.textClinic)
        val textUsername = view.findViewById<TextView>(R.id.textUsername)
        val textPassword = view.findViewById<TextView>(R.id.textPassword)
        val update = view.findViewById<Button>(R.id.edtDoctor)
        val delete = view.findViewById<Button>(R.id.dltDoctor)

        val doctor = list[position]

        textName.text = doctor.Name
        textClinic.text = doctor.Clinic
        textUsername.text = doctor.Username
        textPassword.text = doctor.Password

        update.setOnClickListener{
            val intent = Intent(mCtx, EditDoctor::class.java)
            intent.putExtra(EditDoctor.EXTRA_NAME, doctor.Name)
            intent.putExtra(EditDoctor.EXTRA_ID, doctor.id)
            intent.putExtra(EditDoctor.EXTRA_USERNAME, doctor.Username)
            intent.putExtra(EditDoctor.EXTRA_PASSWORD, doctor.Password)
            mCtx.startActivity(intent)
        }
        delete.setOnClickListener{
            deleteInfo(doctor)
        }

        return view
    }

    private fun deleteInfo(doctor: Doctor) {
        val mydatabase = FirebaseDatabase.getInstance().getReference("Doctor")
        mydatabase.child(doctor.id).removeValue()
        Toast.makeText(mCtx,"Dihapus!",Toast.LENGTH_SHORT).show()
        val intent = Intent(context, ListDoctor::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}