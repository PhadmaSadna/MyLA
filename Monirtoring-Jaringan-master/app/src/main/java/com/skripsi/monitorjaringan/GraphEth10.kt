package com.skripsi.monitorjaringan

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.*
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

class GraphEth10 : AppCompatActivity() {

    lateinit var graphEth101: GraphView
    lateinit var graphEth102: GraphView
    lateinit var graphEth103: GraphView

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
        setContentView(R.layout.activity_graph_eth10)

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
        graphEth101 = findViewById(R.id.graph_eth10)
        graphEth102 = findViewById(R.id.graph_net_in10)
        graphEth103 = findViewById(R.id.graph_net_out10)

        val series: LineGraphSeries<DataPoint> = LineGraphSeries()

        graphEth101.addSeries(series)
        graphEth101.viewport.isXAxisBoundsManual = true
        graphEth101.viewport.setMinX(0.0)
        graphEth101.viewport.setMaxX(totalX.toDouble())

        graphEth102.addSeries(series)
        graphEth102.viewport.isXAxisBoundsManual = true
        graphEth102.viewport.setMinX(0.0)
        graphEth102.viewport.setMaxX(total1X.toDouble())

        graphEth103.addSeries(series)
        graphEth103.viewport.isXAxisBoundsManual = true
        graphEth103.viewport.setMinX(0.0)
        graphEth103.viewport.setMaxX(total2X.toDouble())

        for (data in dataSnapShot.children) {
            //set to graphic
            point = data.child("speed10").getValue().toString()
            point1 = data.child("netin10").getValue().toString()
            point2 = data.child("netout10").getValue().toString()

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
