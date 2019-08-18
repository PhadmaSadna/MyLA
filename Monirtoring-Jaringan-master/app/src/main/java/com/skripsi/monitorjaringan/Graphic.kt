package com.skripsi.monitorjaringan

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.alespero.expandablecardview.ExpandableCardView

class Graphic : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graphic)

        val card : ExpandableCardView = findViewById(R.id.ether5)
        val card1 : ExpandableCardView = findViewById(R.id.ether6)
        val card2 : ExpandableCardView = findViewById(R.id.ether7)
        val card3 : ExpandableCardView = findViewById(R.id.ether9)
        val card4 : ExpandableCardView = findViewById(R.id.ether10)

        card.setOnExpandedListener { view, isExpanded ->
            Toast.makeText(applicationContext, if(isExpanded) "Expanded!" else "Collapsed!", Toast.LENGTH_SHORT).show()
        }

        card1.setOnExpandedListener { view, isExpanded ->
            Toast.makeText(applicationContext, if(isExpanded) "Expanded!" else "Collapsed!", Toast.LENGTH_SHORT).show()
        }

        card2.setOnExpandedListener { view, isExpanded ->
            Toast.makeText(applicationContext, if(isExpanded) "Expanded!" else "Collapsed!", Toast.LENGTH_SHORT).show()
        }

        card3.setOnExpandedListener { view, isExpanded ->
            Toast.makeText(applicationContext, if(isExpanded) "Expanded!" else "Collapsed!", Toast.LENGTH_SHORT).show()
        }

        card4.setOnExpandedListener { view, isExpanded ->
            Toast.makeText(applicationContext, if(isExpanded) "Expanded!" else "Collapsed!", Toast.LENGTH_SHORT).show()
        }

    }
}
