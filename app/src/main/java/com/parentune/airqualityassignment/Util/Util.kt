package com.parentune.airqualityassignment.Util

import androidx.fragment.app.FragmentActivity
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAStyle
import java.util.*

object Util {

    private const val SECOND_MILLIS = 1000
    private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
    private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
    private const val DAY_MILLIS = 24 * HOUR_MILLIS

    fun getTimeAgo(timelong: Long): String {
        var time = timelong
        if (time < 1000000000000L) {
            time *= 1000
        }

        val now = Calendar.getInstance().time.time
        if (time > now || time <= 0) {
            return "in the future"
        }

        val diff = now - time
        return when {
            diff < SECOND_MILLIS -> "second ago"
            diff < MINUTE_MILLIS -> "${diff / SECOND_MILLIS} seconds ago"
            diff < 2 * MINUTE_MILLIS -> "${diff / MINUTE_MILLIS} minute ago"
            diff < 60 * MINUTE_MILLIS -> "${diff / MINUTE_MILLIS} minutes ago"
            diff < 2 * HOUR_MILLIS -> "an hour ago"
            diff < 24 * HOUR_MILLIS -> "${diff / HOUR_MILLIS} hours ago"
            diff < 48 * HOUR_MILLIS -> "yesterday"
            else -> "${diff / DAY_MILLIS} days ago"
        }
    }

    fun configureAAChartModel(activity: FragmentActivity, aqiList: MutableList<Float>): AAChartModel {
        return AAChartModel.Builder(activity)
            .setTitle("AQI")
            .setTitleStyle(AAStyle().color("#FFF").fontSize(15F))
            .setChartType(AAChartType.Spline)
            .setBackgroundColor("#4b2b7f")
            .setDataLabelsEnabled(false)
            .setYAxisGridLineWidth(0f)
            .setLegendEnabled(false)
            .setTouchEventEnabled(true)
            .setSeries(
                AASeriesElement().data(
                    aqiList.toTypedArray()
                )
            )
            .build()
    }

}