package com.skripsi.monitorjaringan


import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.media.app.NotificationCompat
import android.util.Log
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_resource_detail.*


class ResourceDetail : AppCompatActivity() {

    //private var mNotify: Notify? = null

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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNotification() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val NOTIFICATION_CHANNEL_ID = "com.skripsi.monitorjaringan"
        val resultIntent = Intent(getApplicationContext(), MainActivity::class.java)
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        val resultPendingIntent = PendingIntent.getActivity(
            getApplicationContext(),
            0 /* Request code */, resultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification", NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.setDescription("POLINEMA Channel")
            notificationChannel.enableLights(true)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val builder = Notification.Builder(this, NOTIFICATION_CHANNEL_ID)
        builder.setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(android.R.drawable.stat_notify_more)
            .setContentTitle("Warning!")
            .setContentText("Kondisi Jaringan sedang tidak stabil. CPU Load melebihi batas maksimal!")
            .setContentIntent(resultPendingIntent)
        notificationManager.notify(System.currentTimeMillis().toInt(), builder.build())
    }

    private fun showData(dataSnapShot: DataSnapshot) {
        for (data in dataSnapShot.children) {
            cpu_count.text = data.child("cpu_count").getValue().toString()

            //val aaa = buildString { data.child("cpuload").getValue().toString() }
            val aaa = data.child("cpuload").getValue().toString()
            val ccc = aaa.toInt()
            val bbb = 0

            if (ccc >= bbb)
                showNotification()

            cpu_freq.text = data.child("temperature").getValue().toString()
            cpu_load.text = data.child("cpuload").getValue().toString() + " %"
            type.text = data.child("type").getValue().toString()
            sys_os.text = data.child("sys_os").getValue().toString()
            last_change.text = data.child("last_change").getValue().toString()
            total_hdd.text = data.child("totalram").getValue().toString() + " MB"
            used_hdd.text = data.child("usedram").getValue().toString() + " MB"
            progress.dismiss()
        }
    }
}


