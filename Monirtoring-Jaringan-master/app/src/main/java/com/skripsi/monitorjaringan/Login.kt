package com.skripsi.monitorjaringan

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //if not have account
        txt_to_register.setOnClickListener {
            val i = Intent(this, SignUp::class.java)
            startActivity(i)
            overridePendingTransition(
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )
        }

        getData()
    }

    private fun getData() {
        button_login.setOnClickListener {
            val sharedPreferences: SharedPreferences = getSharedPreferences("Register", Context.MODE_PRIVATE)
            val username = sharedPreferences.getString("username", "Register")
            val pass = sharedPreferences.getString("pass", "Register")

            if (ed_username.text.toString() == username && ed_pass.text.toString() == pass) {
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
                overridePendingTransition(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
                )
                finish()
            } else {
                Toast.makeText(this, "Your account not valid!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
