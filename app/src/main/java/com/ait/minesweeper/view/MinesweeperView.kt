package com.ait.minesweeper.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.CompoundButton
import android.widget.ToggleButton
import com.ait.minesweeper.model.MinesweeperModel
import kotlinx.android.synthetic.main.activity_main.view.*
import android.R
import android.widget.Toast
import android.R.id.toggle
import android.graphics.Rect
import com.ait.minesweeper.MainActivity
import com.ait.minesweeper.model.MinesweeperModel.MINE
import com.ait.minesweeper.model.MinesweeperModel.totalNumOfMines
import com.google.android.material.snackbar.Snackbar


class MinesweeperView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    var paintBackground: Paint = Paint()
    var paintLine: Paint = Paint()

    var paintText: Paint = Paint()

    init {
        paintBackground.color = Color.BLACK
        paintBackground.style = Paint.Style.FILL

        paintLine.color = Color.WHITE
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 7f

        paintText.color = Color.RED
        paintText.textSize = 50f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        paintText.textSize = height / 5f

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBackground)

        drawBoard(canvas)

        drawPlayers(canvas)

        if (gameWon == true) {
            drawMines(canvas)
        }
        if (showMines == true) {
            drawMines(canvas)
        }


    }

    private fun drawBoard(canvas: Canvas?) {
        // border
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintLine)
        // four horizontal lines
        canvas?.drawLine(
            0f, (height / 5).toFloat(), width.toFloat(), (height / 5).toFloat(),
            paintLine
        )
        canvas?.drawLine(
            0f, (2 * height / 5).toFloat(), width.toFloat(),
            (2 * height / 5).toFloat(), paintLine
        )
        canvas?.drawLine(
            0f, (3 * height / 5).toFloat(), width.toFloat(),
            (3 * height / 5).toFloat(), paintLine
        )
        canvas?.drawLine(
            0f, (4 * height / 5).toFloat(), width.toFloat(),
            (4 * height / 5).toFloat(), paintLine
        )
        // four vertical lines
        canvas?.drawLine(
            (width / 5).toFloat(), 0f, (width / 5).toFloat(), height.toFloat(),
            paintLine
        )
        canvas?.drawLine(
            (2 * width / 5).toFloat(), 0f, (2 * width / 5).toFloat(), height.toFloat(),
            paintLine
        )
        canvas?.drawLine(
            (3 * width / 5).toFloat(), 0f, (3 * width / 5).toFloat(), height.toFloat(),
            paintLine
        )
        canvas?.drawLine(
            (4 * width / 5).toFloat(), 0f, (4 * width / 5).toFloat(), height.toFloat(),
            paintLine
        )
    }

    private fun drawPlayers(canvas: Canvas?) {
        for (i in 0..4) {
            for (j in 0..4) {
                //circle
                //che
                if (MinesweeperModel.fieldMatrix[i][j].wasClicked && !MinesweeperModel.fieldMatrix[i][j].isFlagged) {
                    /* val centerX = (i * width / 5 + width / 10).toFloat()
                     val centerY = (j * height / 5 + height / 10).toFloat()
                     val radius = height / 11 - 4

                     canvas?.drawCircle(centerX, centerY, radius.toFloat(), paintLine)*/

                    var textToPaint = MinesweeperModel.fieldMatrix[i][j].minesAround.toString()
                    val bounds = Rect()
                    paintText.getTextBounds(textToPaint, 0, textToPaint.length, bounds)
                    val textRealWidth = paintText.measureText(textToPaint)

                    if (MinesweeperModel.fieldMatrix[i][j].minesAround != 0) {
                        canvas?.drawText(
                            MinesweeperModel.fieldMatrix[i][j].minesAround.toString(),
                            (i * (width / 5)).toFloat() + (width / 5) / 2 - textRealWidth / 2, // mid of the cell horizontally
                            ((j + 1) * height / 5).toFloat() - (height / 5) / 2 - bounds.top / 2, // mid of the cell vertically
                            paintText
                        )
                    }
                    //text
                } else if (MinesweeperModel.fieldMatrix[i][j].isFlagged) {
                    val bounds = Rect()
                    paintText.getTextBounds(
                        context.getString(com.ait.minesweeper.R.string.flag_field_text),
                        0,
                        context.getString(com.ait.minesweeper.R.string.flag_field_text).length,
                        bounds
                    )
                    val textRealWidth =
                        paintText.measureText(context.getString(com.ait.minesweeper.R.string.flag_field_text))
                    canvas?.drawText(
                        context.getString(com.ait.minesweeper.R.string.flag_field_text),
                        (i * (width / 5)).toFloat() + (width / 5) / 2 - textRealWidth / 2,
                        ((j + 1) * height / 5).toFloat() - (height / 5) / 2 - bounds.top / 2,
                        paintText
                    )


                }
            }
        }
    }


    var flaggedMines = 0
    var gameWon = false
    var showMines = false
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            val tX = event.x.toInt() / (width / 5)
            val tY = event.y.toInt() / (height / 5)

            //not sure if order is right but this will do for now
            if ((context as MainActivity).isToggleOn()) {
                if (MinesweeperModel.fieldMatrix[tX][tY].isFlagged != true) {
                    flaggedMines += 1
                }
                MinesweeperModel.fieldMatrix[tX][tY].isFlagged = true


                if (MinesweeperModel.fieldMatrix[tX][tY].type != MinesweeperModel.MINE) {
                    Snackbar.make(
                        this@MinesweeperView,
                        context.getString(com.ait.minesweeper.R.string.wrong_placement_text),
                        Snackbar.LENGTH_LONG
                    ).show()
                    showMines = true

                }
                for (i in 0..4) {
                    for (j in 0..4) {
                        if (flaggedMines == totalNumOfMines) {
                            gameWon = true
                            Snackbar.make(
                                this@MinesweeperView,
                                context.getString(com.ait.minesweeper.R.string.game_won_text),
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            } else {
                MinesweeperModel.fieldMatrix[tX][tY].wasClicked = true
                if (MinesweeperModel.fieldMatrix[tX][tY].type == MinesweeperModel.MINE) {
                    Snackbar.make(
                        this@MinesweeperView,
                        context.getString(com.ait.minesweeper.R.string.hit_mine_text),
                        Snackbar.LENGTH_LONG
                    ).show()
                    showMines = true
                }
            }
            invalidate()
        }
        return true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = View.MeasureSpec.getSize(widthMeasureSpec)
        val h = View.MeasureSpec.getSize(heightMeasureSpec)
        val d = if (w == 0) h else if (h == 0) w else if (w < h) w else h
        setMeasuredDimension(d, d)
    }

    fun gameReset() {
        gameWon = false
        showMines = false
        for (i in 0..4) {
            for (j in 0..4) {
                MinesweeperModel.fieldMatrix[i][j].wasClicked = false
                MinesweeperModel.fieldMatrix[i][j].isFlagged = false

            }
        }
        invalidate()
    }

    fun drawMines(canvas: Canvas?) {
        for (i in 0..4) {
            for (j in 0..4) {
                MinesweeperModel.fieldMatrix[i][j].wasClicked = false
                MinesweeperModel.fieldMatrix[i][j].isFlagged = false
                if (MinesweeperModel.fieldMatrix[i][j].type == MINE) {
                    val centerX = (i * width / 5 + width / 10).toFloat()
                    val centerY = (j * height / 5 + height / 10).toFloat()
                    val radius = height / 11 - 4

                    canvas?.drawCircle(centerX, centerY, radius.toFloat(), paintLine)
                }
                invalidate()
            }
        }
    }

}
