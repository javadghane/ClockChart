package ir.javadroid.testhourlychart

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.javadroid.clockchart.model.ChartModel
import ir.javadroid.clockchart.lib.ClockPieView
import ir.javadroid.clockchart.base.ClockChartBase


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val clock: ClockPieView = findViewById(R.id.clk)
        set(clock)

        ClockChartBase.changeAnimationSpeed(10)
    }


    private fun set(clockPieView: ClockPieView) {
        val charts = ArrayList<ChartModel>()
        charts.add(ChartModel(15, 30, 9, 30, Color.parseColor("#9B9A9B")))
        charts.add(ChartModel(11, 50, 14, 30, Color.parseColor("#FFFF00")))
        clockPieView.setDate(charts)
    }
}