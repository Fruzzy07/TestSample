package com.example.historicalfigures

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val monthIndex = intent.getIntExtra("MONTH_INDEX", -1)

        val textViewDetail = findViewById<TextView>(R.id.textview_detail)
        textViewDetail.text = "Detail for month: $monthIndex"

        findViewById<ImageButton>(R.id.button_good).setOnClickListener {
            setResultAndFinish(Activity.RESULT_OK, monthIndex, "GOOD")
        }
        findViewById<ImageButton>(R.id.button_ok).setOnClickListener {
            setResultAndFinish(Activity.RESULT_OK, monthIndex, "OK")
        }
        findViewById<ImageButton>(R.id.button_bad).setOnClickListener {
            setResultAndFinish(Activity.RESULT_OK, monthIndex, "BAD")
        }
    }

    private fun setResultAndFinish(resultCode: Int, monthIndex: Int, status: String) {
        val resultIntent = Intent().apply {
            putExtra("MONTH_INDEX", monthIndex)
            putExtra("STATUS", status)
        }
        setResult(resultCode, resultIntent)
        finish()
    }
}



