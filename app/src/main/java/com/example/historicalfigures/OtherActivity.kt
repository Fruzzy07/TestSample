package com.example.historicalfigures



import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtherActivity : AppCompatActivity() {
    private lateinit var textViewCountryName: TextView
    private lateinit var textViewLifeExpectancy: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other)

        textViewCountryName = findViewById(R.id.textview_country_name)
        textViewLifeExpectancy = findViewById(R.id.textview_life_expectancy)

        // Retrieve the selected country and gender from SharedPreferences
        val sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE)
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
