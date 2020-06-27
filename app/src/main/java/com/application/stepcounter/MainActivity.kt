package com.application.stepcounter

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var mStepCounter: StepCounter? = null
    private var mSensorManager: SensorManager? = null
    private var mAccelerometer: Sensor? = null

    private var steps = 0

    @SuppressLint("SetTextI18n")
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null){
            tv_steps.text = steps.toString()
            tv_kilometers.text = "${Util.calculateKilometers(steps)}km"
            tv_calories.text = "${Util.calculateCalories(steps)}Kcal"
        }

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
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("steps", steps)
    }

    @SuppressLint("SetTextI18n")
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        steps = savedInstanceState.getInt("steps")
        tv_steps.text = steps.toString()
        tv_kilometers.text = "${Util.calculateKilometers(steps)}km"
        tv_calories.text = "${Util.calculateCalories(steps)}Kcal"
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


