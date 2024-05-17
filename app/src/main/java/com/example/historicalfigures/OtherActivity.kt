package com.example.historicalfigures

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class OtherActivity : AppCompatActivity() {
    private lateinit var textViewCountryName: TextView
    private lateinit var textViewLifeExpectancy: TextView
    private lateinit var monthAdapter: MonthAdapter

    private lateinit var detailActivityLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other)

        detailActivityLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                if (data != null) {
                    val monthIndex = data.getIntExtra("MONTH_INDEX", -1)
                    val status = data.getStringExtra("STATUS")
                    if (monthIndex != -1 && status != null) {
                        monthAdapter.updateMonthStatus(monthIndex, status)
                    }
                }
            }
        }

        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH) + 1

        val sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE)
        val dobString = sharedPreferences.getString("dob", "")

        if (!dobString.isNullOrEmpty()) {
            val dobParts = dobString.split("/")
            val dobDay = dobParts[0].toInt()
            val dobMonth = dobParts[1].toInt()
            val dobYear = dobParts[2].toInt()

            val dobCalendar = Calendar.getInstance().apply {
                set(dobYear, dobMonth - 1, dobDay) // Months are indexed from 0
            }

            // Calculate the difference in months
            val yearsDifference = currentYear - dobYear
            val monthsDifference = currentMonth - dobMonth
            val monthsGone = yearsDifference * 12 + monthsDifference

            textViewCountryName = findViewById(R.id.textview_country_name)
            textViewLifeExpectancy = findViewById(R.id.textview_life_expectancy)

            // Retrieve the selected country and gender from SharedPreferences
            val selectedCountry = sharedPreferences.getString("country", "")
            val selectedGender = sharedPreferences.getString("gender", "")

            // Make API call to fetch life expectancy data
            Repository.api.getCountryByName(selectedCountry ?: "").enqueue(object : Callback<List<Country>> {
                override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                    if (response.isSuccessful) {
                        val countries = response.body()
                        // Check if countries list is not empty and contains data for the selected country
                        if (!countries.isNullOrEmpty() && countries.size == 1) {
                            val country = countries[0]
                            val countryName = country.name
                            val lifeExpectancy = if (selectedGender == "Male") country.life_expectancy_male else country.life_expectancy_female
                            textViewCountryName.text = "Country Name: $countryName"
                            textViewLifeExpectancy.text = "Life Expectancy: $lifeExpectancy years"

                            val totalMonths = (lifeExpectancy * 12).toInt()

                            val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                            recyclerView.layoutManager = GridLayoutManager(this@OtherActivity, 12) // 12 columns
                            monthAdapter = MonthAdapter(this@OtherActivity, monthsGone, totalMonths, detailActivityLauncher)
                            recyclerView.adapter = monthAdapter
                        } else {
                            Toast.makeText(this@OtherActivity, "Country not found or data unavailable", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@OtherActivity, "Failed to fetch data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                    Toast.makeText(this@OtherActivity, "Failed to fetch data: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}




