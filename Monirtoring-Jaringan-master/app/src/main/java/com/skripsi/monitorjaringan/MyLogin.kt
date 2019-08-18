package com.skripsi.monitorjaringan

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class MyLogin : AppCompatActivity() {

    lateinit var ed_ip : EditText
    lateinit var ed_comm : EditText
    lateinit var button_login : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_login)

        ed_ip = findViewById(R.id.ed_ip)
        ed_comm = findViewById(R.id.ed_comm)
        button_login = findViewById(R.id.button_login)

        button_login.setOnClickListener {
            saveLogin()
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            overridePendingTransition(
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )
            finish()
        }
    }

    private fun saveLogin(){
        val ip = ed_ip.text.toString().trim()
        val comm = ed_comm.text.toString().trim()

        var ref = FirebaseDatabase.getInstance().getReference("login")
        val ipId = ref.push().key

        val save = SaveIP(ipId, ip, comm)

        if (ipId != null) {
            ref.child(ipId).setValue(save).addOnCompleteListener {
                Toast.makeText(applicationContext, "Success", Toast.LENGTH_LONG)
            }
        }
    }
}
