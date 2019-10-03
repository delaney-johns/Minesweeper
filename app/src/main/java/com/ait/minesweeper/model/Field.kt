package com.ait.minesweeper.model

data class Field(val type: Int, val minesAround: Int,
            var isFlagged: Boolean, var wasClicked: Boolean)