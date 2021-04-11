package ir.javadroid.clockchart.lib

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import ir.javadroid.clockchart.R
import ir.javadroid.clockchart.base.ClockChartBase
import ir.javadroid.clockchart.model.ChartModel
import kotlin.math.cos
import kotlin.math.sin


class ClockChartView constructor(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {


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
        Runnable {
            for (pie in chartLists) {
                pie.update()
            }
            invalidate()
        }
    }

    fun setDate(helperList: ArrayList<ChartModel>) {
        if (helperList.isNotEmpty()) {
            val pieSize = if (chartLists.isEmpty()) 0 else chartLists.size
            for (i in helperList.indices) {
                if (i > pieSize - 1) {
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

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        drawBackground(canvas)
        for (chart in chartLists) {
            val txtPaint = Paint().apply {
                color = chart.color or -0x1000000
                textSize = 40f
            }

            //if(ClockChartBase.isTwentyFourHours){
                canvas.drawArc(cirRect, chart.start, chart.sweep, true, chart.getColorPaint())
          //  }else{
               // canvas.drawArc(cirRect, chart.start*2, chart.sweep*2, true, chart.getColorPaint())
           // }



            val centerAngle = (chart.sweep / 2 + chart.start).toDouble()

            val startX: Double = cos(Math.toRadians(centerAngle)) * pieRadius + cirRect.centerX()
            val startY: Double = sin(Math.toRadians(centerAngle)) * pieRadius + cirRect.centerY()


            var dx = 0f
            var dy = 0f


            val textBounds = Rect()
            txtPaint.getTextBounds(chart.chartTitle, 0, chart.chartTitle.length, textBounds)


            val dxDefault = textBounds.exactCenterX()
            val dyDefault = textBounds.exactCenterY()

            ClockChartBase.log(centerAngle.toString())

            when (centerAngle) {
                //1
                in 360.toDouble()..405.toDouble() -> {
                    dx = 20f
                    dy = 10f
                    ClockChartBase.log(chart.chartTitle + ":1:" + centerAngle + "x:" + dx + " y:" + dy)
                }
                in 405.toDouble()..450.toDouble() -> {
                    dx = 0f
                    dy = 30f
                    ClockChartBase.log(chart.chartTitle + ":1:" + centerAngle + "x:" + dx + " y:" + dy)
                }

                //2
                in 450.toDouble()..495.toDouble() -> {
                    dx = -(textPaint.measureText(chart.chartTitle) / 2)
                    dy = 60f

                    ClockChartBase.log(chart.chartTitle + ":2:" + centerAngle + "x:" + dx + " y:" + dy)
                }
                in 495.toDouble()..540.toDouble() -> {
                    dx = -(textPaint.measureText(chart.chartTitle) + 15)
                    dy = 15f
                    ClockChartBase.log(chart.chartTitle + ":2:" + centerAngle + "x:" + dx + " y:" + dy)
                }

                //3

                in 540.toDouble()..585.toDouble() -> {
                    dx = -(textPaint.measureText(chart.chartTitle) + 15)
                    dy = 15f
                    ClockChartBase.log(chart.chartTitle + ":3:" + centerAngle + "x:" + dx + " y:" + dy)
                }
                in 585.toDouble()..630.toDouble() -> {
                    dx = -(textPaint.measureText(chart.chartTitle) - 15)
                    dy = -15f
                    ClockChartBase.log(chart.chartTitle + ":3:" + centerAngle + "x:" + dx + " y:" + dy)
                }

                //4

                in 270.toDouble()..315.toDouble() -> {
                    dx = -25f
                    dy = -10f
                    ClockChartBase.log(chart.chartTitle + ":4:" + centerAngle + "x:" + dx + " y:" + dy)
                }
                in 315.toDouble()..360.toDouble() -> {
                    dx = 10f
                    dy = 25f
                    ClockChartBase.log(chart.chartTitle + ":4:" + centerAngle + "x:" + dx + " y:" + dy)
                }
            }


            val x = startX.toFloat() + dx
            val y = startY.toFloat() + dy
            canvas.save()
            //canvas.rotate(dz, x, y)
            canvas.drawText(chart.chartTitle, x, y, txtPaint)
            canvas.restore()


        }
    }


    private fun drawBackground(canvas: Canvas) {

        if (ClockChartBase.isShowClockHandler) {
            for (i in 0..11) {
                tempPointLeft[pieCenterPoint.x - (sin(Math.PI / 12 * i) * (pieRadius + lineLength)).toInt()] = pieCenterPoint.y - (cos(Math.PI / 12 * i) * (pieRadius + lineLength)).toInt()
                tempPointRight[pieCenterPoint.x + (sin(Math.PI / 12 * i) * (pieRadius + lineLength)).toInt()] = pieCenterPoint.y + (cos(Math.PI / 12 * i) * (pieRadius + lineLength)).toInt()
                canvas.drawLine(tempPointLeft.x.toFloat(), tempPointLeft.y.toFloat(), tempPointRight.x.toFloat(), tempPointRight.y.toFloat(), linePaint)
            }
        }


        canvas.drawCircle(pieCenterPoint.x.toFloat(), pieCenterPoint.y.toFloat(), pieRadius + lineLength / 2.toFloat(), whitePaint)
        canvas.drawCircle(pieCenterPoint.x.toFloat(), pieCenterPoint.y.toFloat(), pieRadius + lineThickness.toFloat(), linePaint)
        canvas.drawCircle(pieCenterPoint.x.toFloat(), pieCenterPoint.y.toFloat(), pieRadius.toFloat(), whitePaint)

        val clockNumber: ArrayList<String> = arrayListOf("11", "10", "9", "8", "7", "6", "5", "4", "3", "2", "1", "24", "23", "22", "21", "20", "19", "18", "17", "16", "15", "14", "13", "12")
        val clockNumberHalf: ArrayList<String> = arrayListOf("6", "5", "4", "3", "2", "1", "12", "11", "10", "9", "8", "7")
        if (ClockChartBase.isTwentyFourHours) {
            for (i in 24 downTo 1) {
                tempPointRight[pieCenterPoint.x + (sin(Math.PI / 12 * i) * (pieRadius - lineLength)).toInt()] = pieCenterPoint.y + (cos(Math.PI / 12 * i) * (pieRadius - lineLength)).toInt()
                canvas.drawText(clockNumber[i - 1], tempPointRight.x.toFloat(), tempPointRight.y.toFloat() + 15, textPaint)
            }
        } else {
            for ((j, i) in (0..23 step 2).withIndex()) {
                tempPointRight[pieCenterPoint.x + (sin(Math.PI / 12 * i) * (pieRadius - lineLength)).toInt()] = pieCenterPoint.y + (cos(Math.PI / 12 * i) * (pieRadius - lineLength)).toInt()
                canvas.drawText(clockNumberHalf[j], tempPointRight.x.toFloat(), tempPointRight.y.toFloat() + 15, textPaint)
            }
        }


        // canvas.drawText("0", pieCenterPoint.x, topTextHeight, textPaint);
        // canvas.drawText("12", pieCenterPoint.x, mViewHeight, textPaint);
        //canvas.drawText("18", leftTextWidth / 2, pieCenterPoint.y + textRect.height() / 2, textPaint);
        //canvas.drawText("6", mViewWidth - rightTextWidth / 2, pieCenterPoint.y + textRect.height() / 2, textPaint);
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        mViewWidth = measureWidth(widthMeasureSpec)
        mViewHeight = measureHeight(heightMeasureSpec)
        pieRadius = mViewWidth / 2 - lineLength * 2 - (textPaint.measureText("12") / 2).toInt() - textPaint.measureText("2424").toInt()
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
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.clockChart, 0, 0)
        val borderColor = a.getInteger(R.styleable.clockChart_borderColor, 0)
        val textColor = a.getInteger(R.styleable.clockChart_textColor, 0)
        a.recycle()

        ClockChartBase.changeBorderColor(borderColor)
        ClockChartBase.changeTextColor(textColor)


        textPaint.isAntiAlias = true
        textPaint.color = ClockChartBase.defaultTextColor
        textPaint.textSize = textSize.toFloat()
        textPaint.textAlign = Paint.Align.CENTER
        val fm = Paint.FontMetrics()
        textPaint.getFontMetrics(fm)
        textRect = Rect()
        textPaint.getTextBounds("18", 0, 1, textRect)

        linePaint = Paint(textPaint)
        val grayColor = ClockChartBase.defaultBorderColor
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