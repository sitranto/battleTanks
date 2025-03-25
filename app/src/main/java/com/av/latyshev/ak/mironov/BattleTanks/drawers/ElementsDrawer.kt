package com.av.latyshev.ak.mironov.BattleTanks.drawers

import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.view.marginLeft
import androidx.core.view.marginTop
import com.av.latyshev.ak.mironov.BattleTanks.CELL_SIZE
import com.av.latyshev.ak.mironov.BattleTanks.R
import com.av.latyshev.ak.mironov.BattleTanks.binding
import com.av.latyshev.ak.mironov.BattleTanks.enums.Direction
import com.av.latyshev.ak.mironov.BattleTanks.enums.Direction.DOWN
import com.av.latyshev.ak.mironov.BattleTanks.enums.Direction.LEFT
import com.av.latyshev.ak.mironov.BattleTanks.enums.Direction.RIGHT
import com.av.latyshev.ak.mironov.BattleTanks.enums.Direction.UP
import com.av.latyshev.ak.mironov.BattleTanks.enums.Material
import com.av.latyshev.ak.mironov.BattleTanks.models.Coordinate
import com.av.latyshev.ak.mironov.BattleTanks.models.Element
import com.av.latyshev.ak.mironov.BattleTanks.utils.getElementByCoordinates

class ElementsDrawer(val container: FrameLayout) {
    var currentMaterial = Material.EMPTY
    val elementsOnContainer = mutableListOf<Element>()

    fun onTouchContainer(x:Float, y:Float) {
        val topMargin = y.toInt() - (y.toInt() % CELL_SIZE)
        val leftMargin = x.toInt() - (x.toInt() % CELL_SIZE)
        val coordinate = Coordinate(topMargin, leftMargin)
        if(currentMaterial == Material.EMPTY) {
            eraseView(coordinate)
        } else {
            drawOrReplaceView(coordinate)
        }
    }

    private fun drawOrReplaceView(coordinate: Coordinate) {
        val viewOnCoordinate =getElementByCoordinates(coordinate, elementsOnContainer)
        if (viewOnCoordinate == null) {
            selectMaterial(coordinate)
            return
        }
        if ( viewOnCoordinate.material != currentMaterial) {
            replaceView(coordinate)
        }
    }

    private fun replaceView(coordinate: Coordinate) {
        eraseView(coordinate)
        selectMaterial(coordinate)
    }

    private fun eraseView(coordinate: Coordinate){
        val elementOnCoordinate = getElementByCoordinates(coordinate, elementsOnContainer)
        if (elementOnCoordinate != null) {
            val erasingView = container.findViewById<View>(elementOnCoordinate.viewId)
            container.removeView(erasingView)
            elementsOnContainer.remove(elementOnCoordinate)
        }
    }

    fun selectMaterial(coordinate: Coordinate) {
        when (currentMaterial) {
            Material.EMPTY -> {}
            Material.BRICK -> drawView(R.drawable.brick, coordinate)
            Material.CONCRETE -> drawView(R.drawable.concrete, coordinate)
            Material.GRASS -> drawView(R.drawable.grass, coordinate)
            Material.EAGLE -> {
                removeExistingEagle()
                drawView(R.drawable.eagle, coordinate)
            }
        }
    }

    private fun removeExistingEagle() {
        elementsOnContainer.firstOrNull { it.material == Material.EAGLE }?.coordinate?.let { {
                eraseView(it)
            }
        }
    }

    private  fun drawView(
        @DrawableRes image: Int,
        coordinate: Coordinate,
        width: Int = 1,
        height: Int = 1
    ) {
        val view = ImageView(container.context)
        val layoutParams = FrameLayout.LayoutParams(width * CELL_SIZE, height * CELL_SIZE)
        view.setImageResource(image)
        layoutParams.topMargin = coordinate.top
        layoutParams.leftMargin = coordinate.left
        val viewId = View.generateViewId()
        view.id = viewId
        view.layoutParams = layoutParams
        container.addView(view)
        elementsOnContainer.add(Element(viewId, currentMaterial, coordinate, width, height))
    }

}