package com.skripsi.monitorjaringan

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.*
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

class GraphEth7 : AppCompatActivity() {

    lateinit var graphEth71: GraphView
    lateinit var graphEth72: GraphView
    lateinit var graphEth73: GraphView

    lateinit var dataBase: FirebaseDatabase
    lateinit var refernce: DatabaseReference

    var point: String = "0"
    var point1: String = "0"
    var point2: String = "0"

    lateinit var progress : ProgressDialog
    var totalX: Int = 0
    var total1X: Int = 0
    var total2X: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph_eth7)

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
        graphEth71 = findViewById(R.id.graph_eth71)
        graphEth72 = findViewById(R.id.graph_net_in7)
        graphEth73 = findViewById(R.id.graph_net_out7)

        val series: LineGraphSeries<DataPoint> = LineGraphSeries()

        graphEth71.addSeries(series)
        graphEth71.viewport.isXAxisBoundsManual = true
        graphEth71.viewport.setMinX(0.0)
        graphEth71.viewport.setMaxX(totalX.toDouble())

        graphEth72.addSeries(series)
        graphEth72.viewport.isXAxisBoundsManual = true
        graphEth72.viewport.setMinX(0.0)
        graphEth72.viewport.setMaxX(total1X.toDouble())

        graphEth73.addSeries(series)
        graphEth73.viewport.isXAxisBoundsManual = true
        graphEth73.viewport.setMinX(0.0)
        graphEth73.viewport.setMaxX(total2X.toDouble())

        for (data in dataSnapShot.children) {
            //set to graphic
            point = data.child("speed7").getValue().toString()
            point1 = data.child("netin7").getValue().toString()
            point2 = data.child("netout7").getValue().toString()

            val d = DataPoint(totalX.toDouble(), point.toDouble())
            series.appendData(d, true, 10)
            totalX ++

            val d1 = DataPoint(total1X.toDouble(), point.toDouble())
            series.appendData(d1, true, 10)
            total1X ++

            val d2 = DataPoint(total2X.toDouble(), point.toDouble())
            series.appendData(d2, true, 10)
            total2X ++

            progress.dismiss()
        }
    }
}
