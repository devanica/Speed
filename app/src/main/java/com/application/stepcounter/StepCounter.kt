package com.application.stepcounter

import android.app.Service
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.os.IBinder

class StepCounter(private val mStepListener: StepListener) : Service(), SensorEventListener {

    override fun onSensorChanged(event: SensorEvent) {
        if (event.values[0] > 15 || event.values[1] > 15 || event.values[2] > 15) {
            mStepListener.onStepTaken()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    interface StepListener {
        fun onStepTaken()
    }

}