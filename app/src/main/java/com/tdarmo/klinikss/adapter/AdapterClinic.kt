package com.tdarmo.klinikss.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.FirebaseDatabase
import com.tdarmo.klinikss.models.Clinic
import com.tdarmo.klinikss.R
import com.tdarmo.klinikss.activity.AdminDashboard

class AdapterClinic(val mCtx: Context, val layoutResId: Int, val list: List<Clinic>): ArrayAdapter<Clinic>(mCtx,layoutResId,list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View{
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId,null)

        val textName = view.findViewById<TextView>(R.id.textName)
        val textRoom = view.findViewById<TextView>(R.id.textRoom)

        val update = view.findViewById<Button>(R.id.edtClinic)
        val delete = view.findViewById<Button>(R.id.dltClinic)

        val clinic = list[position]

        textName.text = clinic.Name
        textRoom.text = clinic.Room

        update.setOnClickListener{
            showUpdateDialog(clinic)
        }
        delete.setOnClickListener{
            Deleteinfo(clinic)
        }

        return view
    }

    private fun Deleteinfo(clinic: Clinic) {
        val mydatabase = FirebaseDatabase.getInstance().getReference("Clinic")
        mydatabase.child(clinic.Id).removeValue()
        Toast.makeText(mCtx,"Dihapus!",Toast.LENGTH_SHORT).show()
        val intent = Intent(context, AdminDashboard::class.java)
        context.startActivity(intent)
    }

    @SuppressLint("InflateParams")
    private fun showUpdateDialog(clinic: Clinic) {
        val builder = AlertDialog.Builder(mCtx)

        builder.setTitle("Sunting")

        val inflater = LayoutInflater.from(mCtx)

        val view = inflater.inflate(R.layout.update_clinic,null)

        val textName = view.findViewById<EditText>(R.id.inputName)
        val textRoom = view.findViewById<EditText>(R.id.inputRoom)

        textName.setText(clinic.Name)
        textRoom.setText(clinic.Room)

        builder.setView(view)

        builder.setPositiveButton("Ubah") { _, _ ->

            val dbUsers = FirebaseDatabase.getInstance().getReference("Clinic")

            val name = textName.text.toString().trim()
            val room = textRoom.text.toString().trim()

            if (name.isEmpty()){
                textName.error = "Mohon masukkan nama"
                textName.requestFocus()
                return@setPositiveButton
            }

            if (room.isEmpty()){
                textRoom.error = "Mohon masukkan ruangan"
                textRoom.requestFocus()
                return@setPositiveButton
            }

            val clinic = Clinic(clinic.Id,name,room)

            dbUsers.child(clinic.Id).setValue(clinic).addOnCompleteListener{
                Toast.makeText(mCtx,"Diubah", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Batal") { _, _ ->

        }

        val alert = builder.create()
        alert.show()
    }
}