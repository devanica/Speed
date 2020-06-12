package com.application.stepcounter

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener

class StepCounter(private val mStepListener: StepListener) : SensorEventListener {

    override fun onSensorChanged(event: SensorEvent) {
        if (event.values[0] > 20 || event.values[1] > 20 || event.values[2] > 20) {
            mStepListener.onStepTaken()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

    }

    interface StepListener {
        fun onStepTaken()
    }

}