package com.av.latyshev.ak.mironov.BattleTanks

import android.app.Activity
import android.view.View
import android.view.animation.AnimationUtils
import com.av.latyshev.ak.mironov.BattleTanks.activities.SCORE_REQUEST_CODE
import com.av.latyshev.ak.mironov.BattleTanks.activities.ScoreActivity
import com.av.latyshev.ak.mironov.BattleTanks.activities.binding

class GameCore(private val activity: Activity) {
    @Volatile
    private var isPlay = false
    private var isPlayerOrBaseDestroyed = false
    private var isPlayerWin = false

    fun startOrPauseTheGame() {
        isPlay = !isPlay
    }

    fun isPlaying() = isPlay && !isPlayerOrBaseDestroyed && !isPlayerWin

    fun pauseTheGame() {
        isPlay = false
    }

    fun playerWon(score: Int) {
        isPlayerWin = true
        activity.startActivityForResult(ScoreActivity.createIntent(activity, score),
            SCORE_REQUEST_CODE
        )
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