package com.example.historicalfigures

import com.google.gson.annotations.SerializedName

data class HistoricalFigure(
    @SerializedName("name") val name: String,
    @SerializedName("info") val info: Info
)

data class Info(
    @SerializedName("born") val born: String,
    @SerializedName("died") val died: String,
    @SerializedName("occupation") val occupation: List<String>,
    @SerializedName("notable_work") val notableWork: List<String>,
)

