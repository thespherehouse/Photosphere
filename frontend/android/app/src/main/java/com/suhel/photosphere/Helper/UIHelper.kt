package com.suhel.photosphere.Helper

import android.view.MotionEvent
import android.view.View
import com.facebook.rebound.Spring
import com.facebook.rebound.SpringConfig
import com.facebook.rebound.SpringListener
import com.facebook.rebound.SpringSystem

fun View.setupSpringAnimations(tension: Double = 9.0, friction: Double = 0.3, minScale: Float = 0.2f) {
    val spring = SpringSystem.create().createSpring()
    spring.springConfig = SpringConfig.fromOrigamiTensionAndFriction(tension, friction)
    spring.addListener(object : SpringListener {

        override fun onSpringEndStateChange(spring: Spring?) {
        }

        override fun onSpringActivate(spring: Spring?) {
        }

        override fun onSpringUpdate(spring: Spring?) {
            spring?.let {
                val value = it.currentValue.toFloat()
                val scale = 1f - (value * minScale)
                scaleX = scale
                scaleY = scale
            }
        }

        override fun onSpringAtRest(spring: Spring?) {
        }

    })
    spring.endValue = 0.0

    setOnTouchListener {
        _, motionEvent ->

        when (motionEvent.actionMasked) {

            MotionEvent.ACTION_DOWN -> spring.endValue = 1.0

            MotionEvent.ACTION_UP -> spring.endValue = 0.0

        }

        false
    }
}
