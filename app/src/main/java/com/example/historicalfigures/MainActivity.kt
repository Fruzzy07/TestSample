package com.example.historicalfigures

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.historicalfigures.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = HistoricalFigureAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            historicalFigureList.adapter = adapter
            historicalFigureList.layoutManager = LinearLayoutManager(
                this@MainActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            etSearch.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH) {
                    performSearch(etSearch.text.toString())
                    true
                } else false
            }
        }
    }

    private fun performSearch(query: String) {
        Repository.api.getHistoricalFiguresByName(query).enqueue(object :
            Callback<List<HistoricalFigure>> {
            override fun onResponse(
                call: Call<List<HistoricalFigure>>,
                response: Response<List<HistoricalFigure>>
            ) {
                if (response.isSuccessful) {
                    adapter.submitList(response.body())
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Failed to fetch historical figures",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<HistoricalFigure>>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    "Error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}


