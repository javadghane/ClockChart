package ir.javadroid.clockchart.base

import android.content.Context

class ClockChartBase {


    companion object {
        public var defaultAnimationSpeed = 8

        fun changeAnimationSpeed(speedValue: Int) {
            defaultAnimationSpeed = speedValue
        }


        fun dip2px(context: Context, dipValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (dipValue * scale + 0.5f).toInt()
        }

        fun px2dip(context: Context, pxValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (pxValue / scale + 0.5f).toInt()
        }

        fun sp2px(context: Context, spValue: Float): Int {
            val fontScale = context.resources.displayMetrics.scaledDensity
            return (spValue * fontScale + 0.5f).toInt()
        }


        private fun log(log: String) {
            android.util.Log.e("clock", log)
        }
    }


}