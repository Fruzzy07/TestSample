package com.example.historicalfigures


import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val monthIndex = intent.getIntExtra("MONTH_INDEX", -1)

        val textViewDetail = findViewById<TextView>(R.id.textview_detail)
        textViewDetail.text = "Detail for month: $monthIndex"
    }
}
