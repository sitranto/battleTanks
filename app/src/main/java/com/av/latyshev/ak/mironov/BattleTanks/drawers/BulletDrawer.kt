package com.av.latyshev.ak.mironov.BattleTanks.drawers

import android.app.Activity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.av.latyshev.ak.mironov.BattleTanks.CELL_SIZE
import com.av.latyshev.ak.mironov.BattleTanks.R
import com.av.latyshev.ak.mironov.BattleTanks.enums.Direction
import com.av.latyshev.ak.mironov.BattleTanks.models.Coordinate
import com.av.latyshev.ak.mironov.BattleTanks.models.Element
import com.av.latyshev.ak.mironov.BattleTanks.utils.checkViewCanMoveThroughBorder
import com.av.latyshev.ak.mironov.BattleTanks.utils.getElementByCoordinates

private const val BULLET_WIDTH = 15
private const val BULLET_HEIGHT = 15

class BulletDrawer(val container: FrameLayout) {
    private var canBulletGoFuther = true
    private var bulletThread: Thread? = null

    private fun checkBulletThreadDlive() = bulletThread != null && bulletThread!!.isAlive

    fun makeBulletMove(
        myTank: View,
        currentDirection: Direction,
        elementsOnContainer: MutableList<Element>
        ) {
        canBulletGoFuther = true
        if (!checkBulletThreadDlive()) {
            bulletThread = Thread(Runnable {
                val bullet = createBullet(myTank, currentDirection)
                while (bullet.checkViewCanMoveThroughBorder(Coordinate(bullet.top, bullet.left)
                ) && canBulletGoFuther
                ) {
                    when (currentDirection) {
                        Direction.UP -> (bullet.layoutParams as FrameLayout.LayoutParams).topMargin -= BULLET_HEIGHT
                        Direction.DOWN -> (bullet.layoutParams as FrameLayout.LayoutParams).topMargin += BULLET_HEIGHT
                        Direction.LEFT -> (bullet.layoutParams as FrameLayout.LayoutParams).leftMargin -= BULLET_HEIGHT
                        Direction.RIGHT -> (bullet.layoutParams as FrameLayout.LayoutParams).leftMargin += BULLET_HEIGHT
                    }
                    Thread.sleep(30)
                    chooseBehaviorInTermsOfDirections(
                        elementsOnContainer,
                        currentDirection,
                        Coordinate(
                            (bullet.layoutParams as FrameLayout.LayoutParams).topMargin,
                            (bullet.layoutParams as FrameLayout.LayoutParams).leftMargin
                        ))
                    (container.context as Activity).runOnUiThread {
                        container.removeView(bullet)
                        container.addView(bullet)
                    }
                }
                (container.context as Activity).runOnUiThread {
                    container.removeView(bullet)
                }
            })
            bulletThread!!.start()
        }
    }

    private fun chooseBehaviorInTermsOfDirections(
        elementsOnContainer: MutableList<Element>,
        currentDirection: Direction,
        bulletCoordinate: Coordinate
    ) {
        when(currentDirection) {
            Direction.DOWN, Direction.UP -> {
                compareCollections(elementsOnContainer, getCoordinatesForTopOrBottomDirection(bulletCoordinate))
            }

            Direction.LEFT, Direction.RIGHT -> {
                compareCollections(elementsOnContainer, getCoordinatesForLeftOrRightDirection(bulletCoordinate))
            }
        }
    }

    private fun compareCollections(
        elementsOnContainer: MutableList<Element>,
        detectedCoordinateList: List<Coordinate>
    ) {
            detectedCoordinateList.forEach {
                val element = getElementByCoordinates(it, elementsOnContainer)
                removeElementsAndStopBullet(element, elementsOnContainer)
            }
    }

    private fun removeElementsAndStopBullet(
        element: Element?,
        elementsOnContainer: MutableList<Element>
    ) {
        if (element != null) {
            if (element.material.bulletCanGoThrough)
                return
            if (element.material.simpleBulletCanDestroy) {
                stopBullet()
                removeView(element)
                elementsOnContainer.remove(element)
            } else {
                stopBullet()
            }
        }
    }

    private fun stopBullet() {
        canBulletGoFuther = false
    }

    private fun removeView( element: Element?) {
        val activity = container.context as Activity
        activity.runOnUiThread {
            if (element!= null)
                container.removeView(activity.findViewById(element.viewId))
        }
    }

    private fun getCoordinatesForTopOrBottomDirection(bulletCoordinate: Coordinate): List<Coordinate>{
        val leftCell = bulletCoordinate.left - bulletCoordinate.left % CELL_SIZE
        val rightCell = leftCell + CELL_SIZE
        val topCoordinate = bulletCoordinate.top - bulletCoordinate.top % CELL_SIZE
        return listOf(
            Coordinate(topCoordinate, leftCell),
            Coordinate(topCoordinate, rightCell)
        )
    }

    private fun getCoordinatesForLeftOrRightDirection(bulletCoordinate: Coordinate): List<Coordinate>{
        val topCell = bulletCoordinate.top - bulletCoordinate.top % CELL_SIZE
        val bottomCell = topCell + CELL_SIZE
        val leftCoordinate = bulletCoordinate.left - bulletCoordinate.left % CELL_SIZE
        return listOf(
            Coordinate(topCell, leftCoordinate),
            Coordinate(bottomCell, leftCoordinate)
        )
    }

    private fun createBullet(myTank: View, currentDirection: Direction): ImageView {
        return ImageView(container.context)
            .apply {
                this.setImageResource(R.drawable.bullet)
                this.layoutParams = FrameLayout.LayoutParams(BULLET_WIDTH, BULLET_HEIGHT)
                val bulletCoordinate = getBulletCoordinates(this, myTank, currentDirection)
                (this.layoutParams as FrameLayout.LayoutParams).topMargin = bulletCoordinate.top
                (this.layoutParams as FrameLayout.LayoutParams).leftMargin = bulletCoordinate.left
                this.rotation = currentDirection.rotation
            }
    }

    private fun getBulletCoordinates(
        bullet: ImageView,
        myTank: View,
        currentDirection: Direction
    ): Coordinate {
        val tankLeftTopCoordinate = Coordinate(myTank.top, myTank.left)
        return when (currentDirection) {
            Direction.UP -> {
                return Coordinate(
                    top = tankLeftTopCoordinate.top - bullet.layoutParams.height,
                    left = getDistanceToMiddleOfTank(
                        tankLeftTopCoordinate.left, bullet.layoutParams.width
                    )
                )
            }

            Direction.DOWN -> {
                return Coordinate(
                    top = tankLeftTopCoordinate.top + myTank.layoutParams.height,
                    left = getDistanceToMiddleOfTank(
                        tankLeftTopCoordinate.left, bullet.layoutParams.width
                    )
                )
            }

            Direction.LEFT-> {
                return Coordinate(
                    top = getDistanceToMiddleOfTank(
                        tankLeftTopCoordinate.top, bullet.layoutParams.height
                    ),
                    left = tankLeftTopCoordinate.left - bullet.layoutParams.width
                )
            }

            Direction.RIGHT-> {
                return Coordinate(
                    top = getDistanceToMiddleOfTank(
                        tankLeftTopCoordinate.top, bullet.layoutParams.height
                    ),
                    left = tankLeftTopCoordinate.left + myTank.layoutParams.width
                )
            }
        }
    }

    private fun getDistanceToMiddleOfTank(startCoordinate: Int, bulletSize: Int): Int {
        return startCoordinate + (CELL_SIZE - bulletSize / 2)
    }
}