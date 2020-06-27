package com.application.stepcounter

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.util.Log
import java.sql.Timestamp
import java.util.*
import kotlin.time.ExperimentalTime
import kotlin.time.seconds

class StepCounter(private val mStepListener: StepListener) : SensorEventListener {

    private var currentTime = Timestamp(System.currentTimeMillis())
    private var newTime = Timestamp(System.currentTimeMillis())
    private var steps = 0
    private val yAxisValues = ArrayList<Float>()

    @ExperimentalTime
    override fun onSensorChanged(event: SensorEvent) {
        if (event.values[1] >= 10) {
            //Log.e("x axis: ", event.values[0].toString())
            Log.e("y axis: ", event.values[1].toString())
            //Log.e("z axis: ", event.values[2].toString())
            addYAxisValuesToArray(event.values[1])
            newTime = Timestamp(System.currentTimeMillis())
            if (currentTime.time.seconds != newTime.time.seconds && yAxisValues.size > 10){
                currentTime = Timestamp(System.currentTimeMillis())
                mStepListener.onStepTaken()
                yAxisValues.clear()
            }
        }
    }

    fun addYAxisValuesToArray(f: Float) {
        yAxisValues.add(f)
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

    }

    interface StepListener {
        fun onStepTaken()
    }

}