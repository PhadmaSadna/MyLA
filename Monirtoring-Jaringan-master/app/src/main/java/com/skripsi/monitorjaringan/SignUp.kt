package com.skripsi.monitorjaringan

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        getRegister()
        getSaveData()
    }

    private fun getRegister() {
        ed_register_user.text.toString()
        ed_register_pass.text.toString()
        ed_register_verify_pass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (ed_register_verify_pass.text.toString() != ed_register_pass.text.toString()) {
                    ed_register_verify_pass.setError("Password not same!")
                    button_register.isEnabled = false
                } else {
                    button_register.isEnabled = true
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun getSaveData() {
        button_register.setOnClickListener {
            val sharedPreferences: SharedPreferences = getSharedPreferences("Register", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("username", ed_register_user.text.toString())
            editor.putString("pass", ed_register_pass.text.toString())
            editor.putString("verifyPass", ed_register_verify_pass.text.toString())
            editor.apply()
            val i = Intent(this, Login::class.java)
            startActivity(i)
            overridePendingTransition(
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val i = Intent(this, Login::class.java)
        startActivity(i)
        overridePendingTransition(
            R.anim.slide_in_left,
            R.anim.slide_out_right
        )
        finish()
    }
}
