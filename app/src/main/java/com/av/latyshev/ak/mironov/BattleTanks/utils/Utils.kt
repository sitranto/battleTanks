package com.av.latyshev.ak.mironov.BattleTanks.utils

import android.view.View
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
) =
    elementsOnContainer.firstOrNull { it.coordinate == coordinate }