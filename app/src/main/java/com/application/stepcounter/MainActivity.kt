package com.application.stepcounter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var mStepCounter: StepCounter? = null
    private var mSensorManager: SensorManager? = null
    private var mAccelerometer: Sensor? = null

    private var sensorOn = false
    private var steps = 0

    @SuppressLint("SetTextI18n")
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        mStepCounter = StepCounter(object : StepCounter.StepDetector {
            @SuppressLint("SetTextI18n")
            override fun onStepDetected() {
                steps += 1
                tv_steps.text = steps.toString()
                tv_kilometers.text = Util.calculateKilometers(steps)
                tv_calories.text = Util.calculateCalories(steps)
                tv_main_calories.text = Util.calculateCalories(steps)
                tv_main_steps.text = "Steps: $steps"
                iv_on.setImageDrawable(resources.getDrawable(R.drawable.on_element))
                startForegroundService(steps)
            }
        })

        iv_on.setOnClickListener {
            sensorOn = !sensorOn
            if (sensorOn){
                startForegroundService(steps)
            } else{
                stopForegroundService()
            }
        }
    }

    private fun startForegroundService(steps: Int) {
        mSensorManager!!.registerListener(mStepCounter, mAccelerometer, SensorManager.SENSOR_DELAY_UI)
        iv_on.setImageDrawable(resources.getDrawable(R.drawable.on_element))

        val serviceIntent = Intent(this, PedometerService::class.java)
        serviceIntent.putExtra("steps", steps)
        startService(serviceIntent)
    }

    private fun stopForegroundService() {
        mSensorManager!!.unregisterListener(mStepCounter)
        iv_on.setImageDrawable(resources.getDrawable(R.drawable.off_element))

        val serviceIntent = Intent(this, PedometerService::class.java)
        stopService(serviceIntent)
    }
}


