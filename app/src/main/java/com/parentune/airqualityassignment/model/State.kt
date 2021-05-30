package com.parentune.airqualityassignment.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class State(
    @field:Json(name = "city") val city: String,
    @field:Json(name = "aqi") val aqi: String,
    var timeage : Long?
) : Parcelable
