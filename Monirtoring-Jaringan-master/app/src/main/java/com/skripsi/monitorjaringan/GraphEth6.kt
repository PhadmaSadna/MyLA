package com.skripsi.monitorjaringan

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.*
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

class GraphEth6 : AppCompatActivity() {

    lateinit var graphEth61: GraphView
    lateinit var graphEth62: GraphView
    lateinit var graphEth63: GraphView

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
        setContentView(R.layout.activity_graph_eth6)

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
        graphEth61 = findViewById(R.id.graph_network)
        graphEth62 = findViewById(R.id.graph_net_in)
        graphEth63 = findViewById(R.id.graph_net_out)

        val series: LineGraphSeries<DataPoint> = LineGraphSeries()

        graphEth61.addSeries(series)
        graphEth61.viewport.isXAxisBoundsManual = true
        graphEth61.viewport.setMinX(0.0)
        graphEth61.viewport.setMaxX(totalX.toDouble())

        graphEth62.addSeries(series)
        graphEth62.viewport.isXAxisBoundsManual = true
        graphEth62.viewport.setMinX(0.0)
        graphEth62.viewport.setMaxX(total1X.toDouble())

        graphEth63.addSeries(series)
        graphEth63.viewport.isXAxisBoundsManual = true
        graphEth63.viewport.setMinX(0.0)
        graphEth63.viewport.setMaxX(total2X.toDouble())

        for (data in dataSnapShot.children) {
            //set to graphic
            point = data.child("speed6").getValue().toString()
            point1 = data.child("netin6").getValue().toString()
            point2 = data.child("netout6").getValue().toString()

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
