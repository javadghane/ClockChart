package ir.javadroid.clockchart.lib

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import ir.javadroid.clockchart.base.ClockChartBase
import ir.javadroid.clockchart.model.ChartModel
import kotlin.math.cos
import kotlin.math.sin

class ClockPieView constructor(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private val textPaint: Paint = Paint()
    private val linePaint: Paint
    private val whitePaint: Paint
    private var mViewWidth = 0
    private var mViewHeight = 0
    private val textSize: Int = ClockChartBase.sp2px(context, 15f)
    private var pieRadius = 0
    private val pieCenterPoint: Point
    private val tempPointLeft: Point
    private val tempPointRight: Point
    private val lineLength: Int = ClockChartBase.dip2px(context, 10f)
    private val leftTextWidth: Float
    private val rightTextWidth: Float
    private val topTextHeight: Float
    private val lineThickness: Int = ClockChartBase.dip2px(context, 1f)
    private val cirRect: RectF
    private val textRect: Rect
    private val chartLists: ArrayList<ChartModel> = ArrayList()
    private val animator: Runnable by lazy {
        object : Runnable {
            override fun run() {
                var needNewFrame = false
                for (pie in chartLists) {
                    pie.update()
                    if (!pie.isAtRest) {
                        needNewFrame = true
                    }
                }
                if (needNewFrame) {
                    postDelayed(this, 5)
                }
                invalidate()
            }
        }
    }

    fun setDate(helperList: ArrayList<ChartModel>) {
        if (helperList.isNotEmpty()) {
            val pieSize = if (chartLists.isEmpty()) 0 else chartLists.size
            for (i in helperList.indices) {
                if (i > pieSize - 1) {
                    //float mStart = helperList.get(i).getStart();
                    chartLists.add(ChartModel(0f, 0f, helperList[i]))
                } else {
                    chartLists[i] = chartLists[i].setTarget(helperList[i])
                }
            }
            val temp = chartLists.size - helperList.size
            for (i in 0 until temp) {
                chartLists.removeAt(chartLists.size - 1)
            }
        } else {
            chartLists.clear()
        }
        removeCallbacks(animator)
        post(animator)


    }

    override fun onDraw(canvas: Canvas) {

        drawBackground(canvas)
        for (chart in chartLists) {
            canvas.drawArc(cirRect, chart.start, chart.sweep, true, chart.getColorPaint())
        }
    }


    private fun drawBackground(canvas: Canvas) {

        for (i in 0..11) {
            tempPointLeft[pieCenterPoint.x - (sin(Math.PI / 12 * i) * (pieRadius + lineLength)).toInt()] = pieCenterPoint.y - (cos(Math.PI / 12 * i) * (pieRadius + lineLength)).toInt()
            tempPointRight[pieCenterPoint.x + (sin(Math.PI / 12 * i) * (pieRadius + lineLength)).toInt()] = pieCenterPoint.y + (cos(Math.PI / 12 * i) * (pieRadius + lineLength)).toInt()
            canvas.drawLine(tempPointLeft.x.toFloat(), tempPointLeft.y.toFloat(), tempPointRight.x.toFloat(), tempPointRight.y.toFloat(), linePaint)
        }

        canvas.drawCircle(pieCenterPoint.x.toFloat(), pieCenterPoint.y.toFloat(), pieRadius + lineLength / 2.toFloat(), whitePaint)
        canvas.drawCircle(pieCenterPoint.x.toFloat(), pieCenterPoint.y.toFloat(), pieRadius + lineThickness.toFloat(), linePaint)
        canvas.drawCircle(pieCenterPoint.x.toFloat(), pieCenterPoint.y.toFloat(), pieRadius.toFloat(), whitePaint)

        val clockNumber: ArrayList<String> = arrayListOf("11", "10", "9", "8", "7", "6", "5", "4", "3", "2", "1", "24", "23", "22", "21", "20", "19", "18", "17", "16", "15", "14", "13", "12")
        for (i in 24 downTo 1) {
            tempPointRight[pieCenterPoint.x + (sin(Math.PI / 12 * i) * (pieRadius - lineLength)).toInt()] = pieCenterPoint.y + (cos(Math.PI / 12 * i) * (pieRadius - lineLength)).toInt()
            canvas.drawText(clockNumber[i - 1], tempPointRight.x.toFloat(), tempPointRight.y.toFloat() + 15, textPaint)
        }

        // canvas.drawText("0", pieCenterPoint.x, topTextHeight, textPaint);
        // canvas.drawText("12", pieCenterPoint.x, mViewHeight, textPaint);
        //canvas.drawText("18", leftTextWidth / 2, pieCenterPoint.y + textRect.height() / 2, textPaint);
        //canvas.drawText("6", mViewWidth - rightTextWidth / 2, pieCenterPoint.y + textRect.height() / 2, textPaint);
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        mViewWidth = measureWidth(widthMeasureSpec)
        mViewHeight = measureHeight(heightMeasureSpec)
        pieRadius = mViewWidth / 2 - lineLength * 2 - (textPaint.measureText("18") / 2).toInt()
        pieCenterPoint[mViewWidth / 2 - rightTextWidth.toInt() / 2 + leftTextWidth.toInt() / 2] = mViewHeight / 2 + textSize / 2 - (textPaint.measureText("18") / 2).toInt()
        cirRect[(pieCenterPoint.x - pieRadius).toFloat(), (
                pieCenterPoint.y - pieRadius).toFloat(), (
                pieCenterPoint.x + pieRadius).toFloat()] = (
                pieCenterPoint.y + pieRadius).toFloat()
        setMeasuredDimension(mViewWidth, mViewHeight)
    }

    private fun measureWidth(measureSpec: Int): Int {
        val preferred = 3
        return getMeasurement(measureSpec, preferred)
    }

    private fun measureHeight(measureSpec: Int): Int {
        val preferred = mViewWidth
        return getMeasurement(measureSpec, preferred)
    }

    private fun getMeasurement(measureSpec: Int, preferred: Int): Int {
        val specSize = MeasureSpec.getSize(measureSpec)
        return when (MeasureSpec.getMode(measureSpec)) {
            MeasureSpec.EXACTLY -> specSize
            MeasureSpec.AT_MOST -> preferred.coerceAtMost(specSize)
            else -> preferred
        }
    }

    init {
        textPaint.isAntiAlias = true
        val textColor = Color.parseColor("#9B9A9B")
        textPaint.color = textColor
        textPaint.textSize = textSize.toFloat()
        textPaint.textAlign = Paint.Align.CENTER
        val fm = Paint.FontMetrics()
        textPaint.getFontMetrics(fm)
        textRect = Rect()
        textPaint.getTextBounds("18", 0, 1, textRect)

        linePaint = Paint(textPaint)
        val grayColor = Color.parseColor("#D4D3D4")
        linePaint.color = grayColor
        linePaint.strokeWidth = lineThickness.toFloat()
        whitePaint = Paint(linePaint)
        whitePaint.color = Color.WHITE
        tempPointLeft = Point()
        pieCenterPoint = Point()
        tempPointRight = Point()
        cirRect = RectF()
        leftTextWidth = textPaint.measureText("18")
        rightTextWidth = textPaint.measureText("6")
        topTextHeight = textRect.height().toFloat()
    }
}