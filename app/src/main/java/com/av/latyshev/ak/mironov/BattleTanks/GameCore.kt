package com.av.latyshev.ak.mironov.BattleTanks

import android.app.Activity
import android.view.View
import android.view.animation.AnimationUtils

class GameCore(private val activity: Activity) {
    @Volatile
    private var isPlay = false
    private var isPlayerOrBaseDestroyed = false

    fun startOrPauseTheGame() {
        isPlay = !isPlay
    }

    fun isPlaying() = isPlay && !isPlayerOrBaseDestroyed

    fun pauseTheGame() {
        isPlay = false
    }

    fun destroyPlayerOrBase() {
        isPlayerOrBaseDestroyed = true
        pauseTheGame()
        animateEndGame()
    }

    private fun animateEndGame() {
        activity.runOnUiThread {
            binding.gameOverText.visibility = View.VISIBLE
            val slideUp = AnimationUtils.loadAnimation(activity, R.anim.slide_up)
            binding.gameOverText.startAnimation(slideUp)
        }
    }
}