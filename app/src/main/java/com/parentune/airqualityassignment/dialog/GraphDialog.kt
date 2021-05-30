package com.parentune.airqualityassignment.dialog

import android.app.AlertDialog
import android.graphics.Color
import android.view.LayoutInflater
import androidx.fragment.app.FragmentActivity
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.parentune.airqualityassignment.R
import com.parentune.airqualityassignment.Util.Util

class GraphDialog(activity: FragmentActivity, aqiList: MutableList<Float>) : AlertDialog(activity) {

    private val layout = LayoutInflater.from(activity).inflate(R.layout.chart_dialog, null)

    init {
        setView(layout)

        layout.findViewById<AAChartView>(R.id.chart).setBackgroundColor(Color.WHITE)
        layout.findViewById<AAChartView>(R.id.chart)
            ?.aa_drawChartWithChartModel(Util.configureAAChartModel(activity, aqiList))
    }
}