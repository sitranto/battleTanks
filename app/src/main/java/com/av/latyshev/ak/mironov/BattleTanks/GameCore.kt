package com.av.latyshev.ak.mironov.BattleTanks

object GameCore {
    @Volatile
    private var isPlay = false

    fun startOrPauseTheGame() {
        isPlay = !isPlay
    }

    fun isPlaying() = isPlay

    fun pauseTheGame() {
        isPlay = false
    }
}