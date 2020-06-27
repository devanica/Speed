package com.application.stepcounter

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import com.application.stepcounter.Util.addAxisValuesToArray
import java.util.*

class StepCounter(private val mStepDetector: StepDetector) : SensorEventListener {
    private val axisValues = ArrayList<Int>()

    override fun onSensorChanged(event: SensorEvent) {
        if (event.values[1] >= 10 ||
            event.values[0] >= 10 ||
            event.values[2] >= 10 ||
            event.values[0] <= -10 ||
            event.values[1] <= -10 ||
            event.values[2] <= -10) {
            addAxisValuesToArray(axisValues, 1)
            if (axisValues.size > 10){
                mStepDetector.onStepDetected()
                axisValues.clear()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

    }

    interface StepDetector {
        fun onStepDetected()
    }
}