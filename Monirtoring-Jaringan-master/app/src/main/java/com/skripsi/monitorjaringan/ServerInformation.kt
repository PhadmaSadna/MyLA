package com.skripsi.monitorjaringan

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_resource_detail.*
import kotlinx.android.synthetic.main.activity_server_information.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class ServerInformation : AppCompatActivity() {

    lateinit var dataBase: FirebaseDatabase
    lateinit var refernce: DatabaseReference
    lateinit var progress : ProgressDialog

    //val myUptime:String =  "uptime"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_server_information)

        dataBase = FirebaseDatabase.getInstance()
        refernce = dataBase.getReference("network_info")

        refernce.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapShot: DataSnapshot) {
                showData(dataSnapShot)
            }

            override fun onCancelled(dataBaseError: DatabaseError) {
                Log.d("dataBaseError", dataBaseError.toString())
            }
        })
    }

    override fun onStart() {
        super.onStart()
        progress = ProgressDialog(this)
        progress.setMessage("Please wait..")
        progress.show()
    }

    private fun showData(dataSnapShot: DataSnapshot) {
        for (data in dataSnapShot.children) {
            txt_IP.text = data.child("ip").getValue().toString()
            txt_Ports.text = data.child("port").getValue().toString()
            txt_SNMP.text = data.child("comm").getValue().toString()
            txt_type.text = data.child("sys_os").getValue().toString()
            txt_uptime.text = data.child("uptime").getValue().toString() + "Hours"
            txt_status.text = data.child("status").getValue().toString()
            txt_mtu.text = data.child("mtu").getValue().toString()
            progress.dismiss()
        }
    }
}
