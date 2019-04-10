package com.example.scacchiera

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

class ScacchieraView : View {
    var dim = 4
    private var pad = 5
    private var bRectMatrix = generateMatrix()


    private val paint1 = Paint().also { it.color = Color.BLUE }
    private val paint2 = Paint().also { it.color = Color.YELLOW }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val rectHeight = height / dim
        val rectWidth = width / dim
        for (i in 0 until dim) {
            for (j in 0 until dim) {
                if (bRectMatrix[i * dim + j]) {
                    canvas!!.drawRect(
                        (i * rectWidth).toFloat() + pad,
                        (j * rectHeight).toFloat() + pad,
                        ((i + 1) * rectWidth).toFloat() - pad,
                        ((j + 1) * rectHeight).toFloat() - pad,
                        paint1
                    )
                } else {
                    canvas!!.drawRect(
                        (i * rectWidth).toFloat() + pad,
                        (j * rectHeight).toFloat() + pad,
                        ((i + 1) * rectWidth).toFloat() - pad,
                        ((j + 1) * rectHeight).toFloat() - pad,
                        paint2
                    )
                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        val rectHeight = height / dim
        val rectWidth = width / dim
        if (event?.action == MotionEvent.ACTION_DOWN) {

            val riga = (event.y / rectHeight).toInt() + 1
            val col = (event.x / rectWidth).toInt() + 1


            bRectMatrix.apply {
                this.forEachIndexed { index, b ->
                    if (index % dim == riga-1 || (((col-1)*dim) until (col-1)*dim+dim).contains(index)) {
                        this[index] = !b
                    }
                }
            }

            invalidate()
        }

        return super.onTouchEvent(event)
    }

    fun generateMatrix():MutableList<Boolean>{
        return mutableListOf<Boolean>().also {
            var b = true
            for (i in 1..(dim * dim)) {
                it.add(b)
                if (i % dim != 0) {
                    b = !b
                }
            }
        }
    }
}

