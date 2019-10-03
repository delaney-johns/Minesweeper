package com.ait.minesweeper.model

object MinesweeperModel {

    val EMPTY: Int = 1
    val MINE: Int = 2
    val totalNumOfMines = 4
    val fieldMatrix: Array<Array<Field>> = arrayOf(
        arrayOf(
            Field(EMPTY, 1, false, false),
            Field(EMPTY, 1, false, false),
            Field(EMPTY, 1, false, false),
            Field(EMPTY, 1, false, false),
            Field(EMPTY, 1, false, false)
        ),
        arrayOf(
            Field(EMPTY, 1, false, false),
            Field(MINE, 0, false, false),
            Field(EMPTY, 1, false, false),
            Field(MINE, 0, false, false),
            Field(EMPTY, 1, false, false)
        ),
        arrayOf(
            Field(EMPTY, 2, false, false),
            Field(EMPTY, 2, false, false),
            Field(EMPTY, 1, false, false),
            Field(EMPTY, 1, false, false),
            Field(EMPTY, 1, false, false)
        ),
        arrayOf(
            Field(MINE, 0, false, false),
            Field(EMPTY, 1, false, false),
            Field(EMPTY, 1, false, false),
            Field(EMPTY, 1, false, false),
            Field(EMPTY, 1, false, false)
        ),
        arrayOf(
            Field(EMPTY, 1, false, false),
            Field(EMPTY, 1, false, false),
            Field(EMPTY, 1, false, false),
            Field(MINE, 0, false, false),
            Field(EMPTY, 1, false, false)
        )
    )
}