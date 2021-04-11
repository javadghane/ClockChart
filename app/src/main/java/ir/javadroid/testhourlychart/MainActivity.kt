package ir.javadroid.testhourlychart

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.javadroid.clockchart.model.ChartModel
import ir.javadroid.clockchart.lib.ClockChartView
import ir.javadroid.clockchart.base.ClockChartBase


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val clock: ClockChartView = findViewById(R.id.clk)



        ClockChartBase.changeBorderColor(Color.BLACK)
        ClockChartBase.changeTextColor(Color.RED)
        ClockChartBase.showingClockHandler(false)


       // set24Hours(clock)


        set12Hours(clock)
    }


    private fun set12Hours(clockPieView: ClockChartView) {
        ClockChartBase.isTwentyFourHours = false

        val charts = ArrayList<ChartModel>()
        charts.add(ChartModel(4, 30, 6, 0, Color.parseColor("#A321E10C")).setTitle("13:30")) //4-6
        charts.add(ChartModel(9, 30, 12, 0, Color.parseColor("#3343D7E4")).setTitle("13:30")) //4-6
        clockPieView.setDate(charts)
    }

    private fun set24Hours(clockPieView: ClockChartView) {
        ClockChartBase.isTwentyFourHours = true

        val charts = ArrayList<ChartModel>()
        charts.add(ChartModel(1, 0, 2, 0, Color.parseColor("#A321E10C")).setTitle("13:30"))
        charts.add(ChartModel(3, 0, 6, 0, Color.parseColor("#A321E10C")).setTitle("13:30"))

        charts.add(ChartModel(7, 0, 9, 0, Color.parseColor("#3343D7E4")).setTitle("13:30"))
        charts.add(ChartModel(10, 0, 11, 0, Color.parseColor("#3343D7E4")).setTitle("xcn12"))

        charts.add(ChartModel(13, 0, 15, 0, Color.parseColor("#44FA002E")).setTitle("xcn21"))
        charts.add(ChartModel(16, 0, 17, 0, Color.parseColor("#44FA002E")).setTitle("xcn22"))

        charts.add(ChartModel(19, 45, 21, 0, Color.parseColor("#33FA8D2E")).setTitle("xcn31"))
        charts.add(ChartModel(21, 45, 24, 0, Color.parseColor("#33FA8D2E")).setTitle("xcn32"))

        clockPieView.setDate(charts)
    }
}