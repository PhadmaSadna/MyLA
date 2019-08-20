package com.skripsi.monitorjaringan

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.*
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_network_information.*

class NetworkInformation : AppCompatActivity() {

    lateinit var graphInterface: GraphView
    lateinit var graphNetIn: GraphView
    lateinit var graphNetOut: GraphView
    lateinit var dataBase: FirebaseDatabase
    lateinit var refernce: DatabaseReference
    var point: String = "0"
    var pointNetIn: String = "0"
    var pointNetOut: String = "0"
    lateinit var progress : ProgressDialog
    var totalX: Int = 0
    var totalIn: Int = 0
    var totalOut: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network_information)

        dataBase = FirebaseDatabase.getInstance()
        refernce = dataBase.getReference("network_info")

        refernce.keepSynced(true)

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

        //graph interface
        graphInterface = findViewById(R.id.graph_network)
        val series: LineGraphSeries<DataPoint> = LineGraphSeries()
        graphInterface.addSeries(series)
        graphInterface.viewport.isXAxisBoundsManual = true
        graphInterface.viewport.setMinX(0.0)
        graphInterface.viewport.setMaxX(totalX.toDouble())

        //graph net in
        graphNetIn = findViewById(R.id.graph_net_in)
        val seriesNetIn: LineGraphSeries<DataPoint> = LineGraphSeries()
        graphNetIn.addSeries(seriesNetIn)
        graphNetIn.viewport.isXAxisBoundsManual = true
        graphNetIn.viewport.setMinX(0.0)
        graphNetIn.viewport.setMaxX(totalIn.toDouble())

        //graph net out
        graphNetOut = findViewById(R.id.graph_net_out)
        val seriesNetOut: LineGraphSeries<DataPoint> = LineGraphSeries()
        graphNetOut.addSeries(seriesNetOut)
        graphNetOut.viewport.isXAxisBoundsManual = true
        graphNetOut.viewport.setMinX(0.0)
        graphNetOut.viewport.setMaxX(totalOut.toDouble())

        for (data in dataSnapShot.children) {
            names1.text = data.child("descr").getValue().toString()
            address1.text = data.child("ip").getValue().toString()

            //set to graphic
            point = data.child("speed").getValue().toString()
            pointNetIn = data.child("net_in").getValue().toString()
            pointNetOut = data.child("net_out").getValue().toString()

            val d = DataPoint(totalX.toDouble(), point.toDouble())
            series.appendData(d, true, 10)
            totalX ++

            val dataIn = DataPoint(totalIn.toDouble(), pointNetIn.toDouble())
            seriesNetIn.appendData(dataIn, true, 10)
            totalIn ++

            val dataOut = DataPoint(totalOut.toDouble(), pointNetOut.toDouble())
            seriesNetOut.appendData(dataOut, true, 10)
            totalOut ++

            names2.text = data.child("descr").getValue().toString()
            address2.text = data.child("ip").getValue().toString()
            progress.dismiss()
        }
    }


}
