package com.application.stepcounter

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var mStepCounter: StepCounter? = null
    private var mSensorManager: SensorManager? = null
    private var mAccelerometer: Sensor? = null

    private var steps = 0

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mStepCounter = StepCounter(object : StepCounter.OnShakeListener {
            override fun onShake() {
                Toast.makeText(applicationContext, "Sensors are working", Toast.LENGTH_SHORT)
                    .show()
                steps =+ 1
                savedInstanceState!!.putInt("steps", steps)
            }
        })
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        steps = savedInstanceState.getInt("steps")
    }

    override fun onResume() {
        super.onResume()
        mSensorManager!!.registerListener(mStepCounter, mAccelerometer, SensorManager.SENSOR_DELAY_UI)
    }

    override fun onPause() {
        mSensorManager!!.unregisterListener(mStepCounter)
        super.onPause()
    }
}


