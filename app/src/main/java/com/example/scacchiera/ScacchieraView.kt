package com.example.scacchiera

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.design.widget.Snackbar
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class ScacchieraView : View {
    var dim = 4
    var isPlaying = false
    private var pad = 5
    private var bRectMatrix = generateMatrix()
    private var victories = 0
    private var moves = 0

    private lateinit var moveListener: onMoveListener
    private lateinit var victoryListener: onVictoryListener

    private val paint1 = Paint().also { it.color = Color.BLUE }
    private val paint2 = Paint().also { it.color = Color.YELLOW }

    private val directions = mapOf(
        "up" to Pair(0, 1),
        "down" to Pair(0, -1),
        "right" to Pair(1, 0),
        "left" to Pair(-1, 0)
    )

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
            if (isPlaying) {
                val riga = (event.y / rectHeight).toInt()
                val col = (event.x / rectWidth).toInt()

                bRectMatrix.apply {
                    this.forEachIndexed { index, b ->
                        if (index % dim == riga || ((col * dim) until col * dim + dim).contains(index)) {
                            this[index] = !b
                        }
                    }
                }

                invalidate()

                moveListener.onMove(++moves)

                if (hasWon()) {
                    isPlaying = false
                    Snackbar.make(this, "Complimenti!! Hai vinto in $moves mosse.", Snackbar.LENGTH_LONG).show()
                    victoryListener.onVictory(++victories)
                    regenerateMatrix()
                }

            } else {
                Snackbar.make(this, "Premi Gioca per cominciare a giocare.", Snackbar.LENGTH_LONG).show()
            }
        }

        return super.onTouchEvent(event)
    }

    private fun generateMatrix(): MutableList<Boolean> {
        return mutableListOf<Boolean>().also {
            var b = true
            for (i in 1..(dim * dim)) {
                it.add(b)
                if (i % dim != 0 || dim % 2 != 0) {
                    b = !b
                }
            }
        }
    }

    fun regenerateMatrix() {
        bRectMatrix = generateMatrix()
        moves = 0
        moveListener.onMove(0)
        invalidate()
    }

    private fun hasWon(): Boolean {
        return !bRectMatrix.contains(false)
    }


    fun setOnVictoryListener(listener: (n: Int) -> Unit) {
        victoryListener = object : onVictoryListener {
            override fun onVictory(n: Int) {
                listener(n)
            }
        }
    }

    fun setOnMoveListener(listener: (n: Int) -> Unit) {
        moveListener = object : onMoveListener {
            override fun onMove(n: Int) {
                listener(n)
            }
        }
    }

    private fun shiftMatrix(direction: Pair<Int, Int>) {
        val (dx, dy) = direction
        val newMatrix = bRectMatrix.toMutableList()
        for (i in 0 until dim) {
            for (j in 0 until dim) {
                val newIndex = (i + (dim - dx)) % dim * dim + (j + (dim - dy)) % dim
                newMatrix[newIndex] = bRectMatrix[i * dim + j]
            }
        }
        bRectMatrix = newMatrix
    }

    companion object {
        interface onVictoryListener {
            fun onVictory(n: Int)
        }

        interface onMoveListener {
            fun onMove(n: Int)
        }
    }


}