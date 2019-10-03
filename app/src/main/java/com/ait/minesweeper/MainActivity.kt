package com.ait.minesweeper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ait.minesweeper.view.MinesweeperView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resetBtn.setOnClickListener {
            minesweeperView.gameReset()
        }
    }

    fun isToggleOn(): Boolean {
        return toggleBtn.isChecked
    }

}
