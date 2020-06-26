package com.application.stepcounter

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import java.sql.Timestamp
import kotlin.time.seconds

class StepCounter(private val mStepListener: StepListener) : SensorEventListener {

    var currentTime = Timestamp(System.currentTimeMillis())
    var newTime = Timestamp(System.currentTimeMillis())
    var currentStep = 0

    override fun onSensorChanged(event: SensorEvent) {
        if (event.values[0] > 20 || event.values[1] > 20 || event.values[2] > 20) {
            newTime = Timestamp(System.currentTimeMillis())
            if (currentTime.seconds != newTime.seconds){
                if (currentStep == 1){
                    currentStep = 0
                    return
                }
                currentTime = Timestamp(System.currentTimeMillis())
                mStepListener.onStepTaken()
                currentStep = 1
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

    }

    interface StepListener {
        fun onStepTaken()
    }

}