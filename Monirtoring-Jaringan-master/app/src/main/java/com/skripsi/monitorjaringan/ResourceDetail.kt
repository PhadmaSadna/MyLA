package com.skripsi.monitorjaringan

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_network_information.*
import kotlinx.android.synthetic.main.activity_resource_detail.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class ResourceDetail : AppCompatActivity() {

    lateinit var dataBase: FirebaseDatabase
    lateinit var refernce: DatabaseReference
    lateinit var progress : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resource_detail)

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
            cpu_count.text = data.child("cpuload").getValue().toString()
            cpu_freq.text = data.child("temperature").getValue().toString()
            cpu_load.text = data.child("cpu_freq").getValue().toString() + " %"
            type.text = data.child("type").getValue().toString()
            sys_os.text = data.child("sys_os").getValue().toString()
            last_change.text = data.child("last_change").getValue().toString()
            total_hdd.text = data.child("totalram").getValue().toString() + " MB"
            used_hdd.text = data.child("usedram").getValue().toString() + " MB"
            progress.dismiss()
        }
    }
}
