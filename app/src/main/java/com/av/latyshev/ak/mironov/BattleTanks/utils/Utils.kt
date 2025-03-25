package com.av.latyshev.ak.mironov.BattleTanks.utils

import android.view.View
import com.av.latyshev.ak.mironov.BattleTanks.CELL_SIZE
import com.av.latyshev.ak.mironov.BattleTanks.binding
import com.av.latyshev.ak.mironov.BattleTanks.models.Coordinate
import com.av.latyshev.ak.mironov.BattleTanks.models.Element

fun View.checkViewCanMoveThroughBorder(coordinate: Coordinate): Boolean {
    return coordinate.top >= 0 &&
            coordinate.top + this.height <= binding.container.height &&
            coordinate.left >= 0 &&
            coordinate.left + this.width <= binding.container.width
}

fun getElementByCoordinates(
    coordinate: Coordinate,
    elementsOnContainer: List<Element>
): Element? {
    for (element in elementsOnContainer) {
        for (height in 0  until   element.height) {
            for (wigth in 0 until  element.width) {
                val searchingCoordinate = Coordinate(
                    top = element.coordinate.top + height * CELL_SIZE,
                    left = element.coordinate.left + wigth * CELL_SIZE
                )
                if (coordinate == searchingCoordinate) {
                    return element
                }
            }
        }
    }
    return null
}
