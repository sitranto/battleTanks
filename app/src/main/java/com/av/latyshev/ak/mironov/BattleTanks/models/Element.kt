package com.av.latyshev.ak.mironov.BattleTanks.models

import android.view.View
import com.av.latyshev.ak.mironov.BattleTanks.enums.Material

data class Element constructor(
    val viewId: Int = View.generateViewId(),
    val material: Material,
    val coordinate: Coordinate,
    val width: Int,
    val height: Int
) {
}