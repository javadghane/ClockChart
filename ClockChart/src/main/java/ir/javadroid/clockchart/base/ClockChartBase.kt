package ir.javadroid.clockchart.base

import android.content.Context
import android.graphics.Color

class ClockChartBase {


    companion object {
        public var defaultAnimationSpeed = 50
        public var defaultTextColor = Color.parseColor("#D4D3D4")
        public var defaultBorderColor = Color.parseColor("#9B9A9B")
        public var isShowClockHandler = true

        fun changeAnimationSpeed(speedValue: Int) {
            defaultAnimationSpeed = speedValue
        }

        fun changeTextColor(color: Int) {
            defaultTextColor = color
        }

        fun changeBorderColor(color: Int) {
            defaultBorderColor = color
        }
        fun showingClockHandler(isShow:Boolean) {
            isShowClockHandler = isShow
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


         fun log(log: String) {
            android.util.Log.e("clock", log)
        }
    }


}