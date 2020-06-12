package com.application.stepcounter

import android.app.Service
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.os.IBinder

class StepCounter(private val mShakeListener: OnShakeListener) : Service(), SensorEventListener {

    override fun onSensorChanged(event: SensorEvent) {
        if (event.values[0] > 15 || event.values[1] > 15 || event.values[2] > 15) {
            mShakeListener.onShake()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    interface OnShakeListener {
        fun onShake()
    }

}