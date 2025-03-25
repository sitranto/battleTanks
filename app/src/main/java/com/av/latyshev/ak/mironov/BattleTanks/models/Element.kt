package com.av.latyshev.ak.mironov.BattleTanks.models

import com.av.latyshev.ak.mironov.BattleTanks.enums.Material

data class Element(
    val viewId: Int,
    val material: Material,
    val coordinate: Coordinate,
    val width: Int,
    val height: Int
) {
}