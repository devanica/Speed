package com.application.stepcounter

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation.*
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import kotlinx.coroutines.*

class BrandingScreen : AppCompatActivity() {
    var job: Job? = null
    var jobSecond: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_branding_screen)

        val leftSneaker = findViewById<ImageView>(R.id.iv_sneaker_left)
        val rightSneaker = findViewById<ImageView>(R.id.iv_sneaker_right)
        animateSneaker(leftSneaker)
        delayAnimSneaker(rightSneaker)

        jobSecond = CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Main){
                delay(3000)
                val i = Intent(applicationContext, MainActivity::class.java)
                startActivity(i)

                leftSneaker.clearAnimation()
                rightSneaker.clearAnimation()
            }
        }
    }

    private fun delayAnimSneaker(view: ImageView) {
        // Do an asynchronous operation to fetch users.
        job = CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Main){
                delay(200)
                animateSneaker(view)
            }
        }
    }

    private fun animateSneaker(view: ImageView){
        val rotate = RotateAnimation(
            0f,
            45f,
            0,
            0f,
            0,
            0f
        )
        rotate.duration = 200
        rotate.repeatMode = REVERSE
        rotate.repeatCount = INFINITE
        rotate.interpolator = LinearInterpolator()

        view.startAnimation(rotate)
    }
}
