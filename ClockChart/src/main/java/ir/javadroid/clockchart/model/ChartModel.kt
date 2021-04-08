package ir.javadroid.clockchart.model

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import ir.javadroid.clockchart.base.ClockChartBase.Companion.defaultAnimationSpeed
import kotlin.math.abs

class ChartModel {
    var start: Float
    var end: Float
    private var targetStart = 0f
    private var targetEnd = 0f

    companion object {
        public var defaultColor = Color.argb(50, 255, 0, 51)
    }

    constructor(startDegree: Float, endDegree: Float, targetPie: ChartModel) {
        start = startDegree
        end = endDegree
        targetStart = targetPie.start
        targetEnd = targetPie.end
    }

    constructor(startHour: Int, startMin: Int, endHour: Int, endMin: Int, color: Int) {
        start = (270 + startHour * 15 + startMin * 15 / 60).toFloat()
        end = (270 + endHour * 15 + endMin * 15 / 60).toFloat()
        while (end < start) {
            end += 360f
        }
        defaultColor = color
    }

    constructor(startHour: Int, startMin: Int, startSec: Int, endHour: Int, endMin: Int, endSec: Int, color: Int) {
        start = (270 + startHour * 15 + startMin * 15 / 60 + startSec * 15 / 3600).toFloat()
        end = (270 + endHour * 15 + endMin * 15 / 60 + endSec * 15 / 3600).toFloat()
        while (end < start) {
            end += 360f
        }
        defaultColor = color
    }

    fun setColor(color: Int): ChartModel {
        defaultColor = color
        return this
    }

    fun getColorPaint(): Paint {
        return Paint().apply { color = defaultColor }
    }

    fun setTarget(targetStart: Float, targetEnd: Float): ChartModel {
        this.targetStart = targetStart
        this.targetEnd = targetEnd
        return this
    }

    fun setTarget(targetPie: ChartModel): ChartModel {
        targetStart = targetPie.start
        targetEnd = targetPie.end
        return this
    }

    val isAtRest: Boolean get() = start == targetStart && end == targetEnd

    fun update() {
        start = updateSelf(start, targetStart, defaultAnimationSpeed)
        end = updateSelf(end, targetEnd, defaultAnimationSpeed)
    }

    val sweep: Float get() = end - start

    private fun updateSelf(origin: Float, target: Float, velocity: Int): Float {
        var originSelf = origin
        if (originSelf < target) {
            originSelf += velocity.toFloat()
        } else if (originSelf > target) {
            originSelf -= velocity.toFloat()
        }
        if (abs(target - originSelf) < velocity) {
            originSelf = target
        }
        return originSelf
    }
}