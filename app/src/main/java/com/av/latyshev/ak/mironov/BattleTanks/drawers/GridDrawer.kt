package com.av.latyshev.ak.mironov.BattleTanks.drawers

import android.graphics.Color
import android.view.View
import android.widget.FrameLayout
import com.av.latyshev.ak.mironov.BattleTanks.CELL_SIZE
import com.av.latyshev.ak.mironov.BattleTanks.binding

class GridDrawer(private val context: FrameLayout) {
    private val allLines = mutableListOf<View>()

    fun drawGrid() {
        val container = binding.container
        drawHorizontalLines(container)
        drawVerticalLines(container)
    }

    fun removeGrid() {
        val container = binding.container
        allLines.forEach {
            container.removeView(it)
        }
    }

    private fun drawHorizontalLines(container: FrameLayout?) {
        var topMargin = 0
        while (topMargin <= container!!.height) {
            val horizontalLine = View(container.context)
            val layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 1)
            topMargin += CELL_SIZE
            layoutParams.topMargin = topMargin
            horizontalLine.layoutParams = layoutParams
            horizontalLine.setBackgroundColor(Color.WHITE)
            allLines.add(horizontalLine)
            container.addView(horizontalLine)
        }
    }

    private fun drawVerticalLines(container: FrameLayout?) {
        var leftMargin = 0
        while (leftMargin <= container!!.width) {
            val verticalLine = View(container.context)
            val layoutParams = FrameLayout.LayoutParams(1, FrameLayout.LayoutParams.MATCH_PARENT)
            leftMargin += CELL_SIZE
            layoutParams.leftMargin = leftMargin
            verticalLine.layoutParams = layoutParams
            verticalLine.setBackgroundColor(Color.WHITE)
            allLines.add(verticalLine)
            container.addView(verticalLine)
        }
    }
}