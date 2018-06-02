package com.divyanshu.draw.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CircleView(context: Context, attrs: AttributeSet): View(context, attrs) {
    private var mPaint = Paint()
    var radius = 8f

    init {
        mPaint.apply {
            color = Color.BLACK
            style = Paint.Style.FILL
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = canvas.width.toFloat()
        val height = canvas.height.toFloat()
        val cX = width.div(2)
        val cY = height.div(2)

        canvas.drawCircle(cX, cY, radius/2, mPaint)
    }

    fun setCircleRadius(r: Float){
        radius = r
        invalidate()
    }

    fun setAlpha(newAlpha: Int){
        val alpha = (newAlpha*255)/100
        mPaint.alpha = alpha
        invalidate()
    }

    fun setColor(color: Int){
        mPaint.color = color
        invalidate()
    }
}