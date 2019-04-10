package com.example.scacchiera

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class ScacchieraView : View {
    var dim = 4
    private val bRectMatrix = ArrayList<Boolean>().also {
        var b=true
        for (i in 0 until (dim*dim)){
            it[i]=b
            b=!b
        }
    }
    private val paint1=Paint().also { it.color=Color.BLUE }
    private val paint2=Paint().also { it.color=Color.YELLOW }
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val rectHeight = this.height / dim
        val rectWidth = this.width / dim


        for(i in 0 until dim){
            for(j in 0 until dim){
                if(bRectMatrix[i*dim+j]){
                    canvas!!.drawRect((i*rectWidth).toFloat(),(j*rectHeight).toFloat(),((i+1)*rectWidth).toFloat(),((j+1)*rectHeight).toFloat(),paint1)
                }else{
                    canvas!!.drawRect((i*rectWidth).toFloat(),(j*rectHeight).toFloat(),((i+1)*rectWidth).toFloat(),((j+1)*rectHeight).toFloat(),paint2)

                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if ( event?.action == MotionEvent.ACTION_UP){
            val riga = (event.y/dim).toInt()
            val col=(event.x/dim).toInt()



        }

        return super.onTouchEvent(event)
    }
}

