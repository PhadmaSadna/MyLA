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

class GraphEth5 : AppCompatActivity() {

    lateinit var graphEth51: GraphView
    lateinit var graphEth52: GraphView
    lateinit var graphEth53: GraphView

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
        setContentView(R.layout.activity_graph_eth5)

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
        graphEth51 = findViewById(R.id.graph_network)
        graphEth52 = findViewById(R.id.graph_net_in)
        graphEth53 = findViewById(R.id.graph_net_out)

        val series: LineGraphSeries<DataPoint> = LineGraphSeries()

        graphEth51.addSeries(series)
        graphEth51.viewport.isXAxisBoundsManual = true
        graphEth51.viewport.setMinX(0.0)
        graphEth51.viewport.setMaxX(totalX.toDouble())

        graphEth52.addSeries(series)
        graphEth52.viewport.isXAxisBoundsManual = true
        graphEth52.viewport.setMinX(0.0)
        graphEth52.viewport.setMaxX(total1X.toDouble())

        graphEth53.addSeries(series)
        graphEth53.viewport.isXAxisBoundsManual = true
        graphEth53.viewport.setMinX(0.0)
        graphEth53.viewport.setMaxX(total2X.toDouble())

        for (data in dataSnapShot.children) {
            //set to graphic
            point = data.child("speed5").getValue().toString()
            point1 = data.child("netin5").getValue().toString()
            point2 = data.child("netout5").getValue().toString()

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
