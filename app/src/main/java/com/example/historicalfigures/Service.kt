package com.example.historicalfigures

import com.example.historicalfigures.HistoricalFigure
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface Service {
    @Headers("X-Api-Key:Y0Oayb616M8R6GJEWIOvfA==bqYEurhfiYekaQPw")
    @GET("historicalfigures")
    fun getHistoricalFiguresByName(@Query("name") name: String): Call<List<HistoricalFigure>>
}
