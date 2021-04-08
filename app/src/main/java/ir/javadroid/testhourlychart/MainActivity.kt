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


        //ClockChartBase.changeAnimationSpeed(10)
        ClockChartBase.changeBorderColor(Color.BLACK)
        ClockChartBase.changeTextColor(Color.RED)
        ClockChartBase.showingClockHandler(false)

        set(clock)
    }


    private fun set(clockPieView: ClockChartView) {
        val charts = ArrayList<ChartModel>()
        /*  charts.add(ChartModel(15, 30, 22, 30, Color.parseColor("#3343D7E4")).setTitle("salam"))
          charts.add(ChartModel(21, 50, 6, 30, Color.parseColor("#44FAFF2E")).setTitle("123"))
          charts.add(ChartModel(8, 50, 10, 30, Color.parseColor("#44FA002E")).setTitle("qqq"))
          charts.add(ChartModel(11, 50, 14, 30, Color.parseColor("#33FA8D2E")).setTitle("zx"))*/

        //charts.add(ChartModel(10, 1, 18, 0, Color.parseColor("#3343D7E4")).setTitle("10-18"))
        charts.add(ChartModel(5, 0, 10, 0, Color.parseColor("#3343D7E4")).setTitle("star"))
        charts.add(ChartModel(19, 0, 23, 0, Color.parseColor("#44FAFF2E")).setTitle("1923"))
        charts.add(ChartModel(1, 0, 4, 0, Color.parseColor("#44FA00ff")).setTitle("1040"))
        charts.add(ChartModel(11, 0, 18, 0, Color.parseColor("#33FA8D2E")).setTitle("11020"))

        clockPieView.setDate(charts)
    }
}