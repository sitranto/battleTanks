package com.av.latyshev.ak.mironov.BattleTanks.enums

enum class Material(
    val tankCanGoThrough: Boolean,
    val bulletCanGoThrough: Boolean,
    val simpleBulletCanDestroy: Boolean
) {
    EMPTY(true, true, true ),
    BRICK(false, false, true),
    CONCRETE(false, false, false),
    GRASS(true, true, true),
}