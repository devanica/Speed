package com.application.stepcounter

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.util.Log
import com.application.stepcounter.Util.addYAxisValuesToArray
import java.util.*

class StepCounter(private val mStepDetector: StepDetector) : SensorEventListener {
    private val yAxisValues = ArrayList<Float>()

    override fun onSensorChanged(event: SensorEvent) {
        if (event.values[1] >= 10) {
            Log.e("y axis: ", event.values[1].toString())
            addYAxisValuesToArray(yAxisValues, event.values[1])
            if (yAxisValues.size > 10){
                mStepDetector.onStepDetected()
                yAxisValues.clear()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

    }

    interface StepDetector {
        fun onStepDetected()
    }
}