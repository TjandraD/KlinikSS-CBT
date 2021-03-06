package com.tdarmo.klinikss.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.tdarmo.klinikss.R
import com.tdarmo.klinikss.adapter.AdapterRegisAdmin
import com.tdarmo.klinikss.models.Regist
import com.tdarmo.klinikss.prevalent.Prevalent
import io.paperdb.Paper
import java.lang.NullPointerException

class DoctorDashboard : AppCompatActivity() {

    private lateinit var ref : DatabaseReference
    lateinit var list : MutableList<Regist>
    private lateinit var listView : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_dashboard)
        val actionBar = supportActionBar
        actionBar!!.title = "Dashboard Dokter"

        val x = Paper.book().read<String>(Prevalent.doctorName)
        Toast.makeText(this, x, Toast.LENGTH_SHORT).show()

        ref = FirebaseDatabase.getInstance().getReference("Registration")
        list = mutableListOf()
        listView = this.findViewById(R.id.listViewDoctorDashboard)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError){

            }
            override fun onDataChange(p0: DataSnapshot){
                list.clear()
                if (p0.exists()){
                    for (h in p0.children){
                        val a = h.getValue(Regist::class.java)
                        val doctorEmail = Paper.book().read<String>(Prevalent.doctorName)
                        if (a != null) {
                            if (a.Doctor == doctorEmail){
                                list.add(a)
                            }
                        }
                    }
                    val adapter = AdapterRegisAdmin(
                        this@DoctorDashboard,
                        R.layout.doctor_dashboard,
                        list
                    )
                    listView.adapter = adapter
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun setMode(selectedMode: Int) {
        when (selectedMode) {
            R.id.action_logout -> {
                FirebaseAuth.getInstance().signOut()
                Toast.makeText(this, "Anda berhasil keluar", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
    }
}
