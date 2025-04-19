package com.av.latyshev.ak.mironov.BattleTanks.models

import android.view.View
import com.av.latyshev.ak.mironov.BattleTanks.enums.Direction

class Bullet(
    val view: View,
    val direction: Direction,
    val tank: Tank,
    var canMoveFurther: Boolean = true
) {

}
