package com.application.stepcounter

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.util.Log
import java.sql.Timestamp
import kotlin.time.ExperimentalTime
import kotlin.time.milliseconds
import kotlin.time.seconds

class StepCounter(private val mStepListener: StepListener) : SensorEventListener {

    var currentTime = Timestamp(System.currentTimeMillis())
    var newTime = Timestamp(System.currentTimeMillis())

    @ExperimentalTime
    override fun onSensorChanged(event: SensorEvent) {
        if (event.values[1] >= 10) {
            Log.e("x axis: ", event.values[0].toString())
            Log.e("y axis: ", event.values[1].toString())
            Log.e("z axis: ", event.values[2].toString())
            newTime = Timestamp(System.currentTimeMillis())
            if (currentTime.time.seconds != newTime.time.seconds){
                currentTime = Timestamp(System.currentTimeMillis())
                mStepListener.onStepTaken()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

    }

    interface StepListener {
        fun onStepTaken()
    }

}