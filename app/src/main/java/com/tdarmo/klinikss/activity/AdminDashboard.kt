package com.tdarmo.klinikss.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.tdarmo.klinikss.R
import kotlinx.android.synthetic.main.admin_dashboard.*

class AdminDashboard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_dashboard)
        val actionBar = supportActionBar
        actionBar!!.title = "Admin Dashboard"

        btnRegis.setOnClickListener{
            val intent = Intent(this, ListRegisAdmin::class.java)
            startActivity(intent)
        }

        btnClinic.setOnClickListener{
            val intent = Intent(this, ListClinic::class.java)
            startActivity(intent)
        }

        btnDoctor.setOnClickListener{
            val intent = Intent(this, ListDoctor::class.java)
            startActivity(intent)
        }
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
