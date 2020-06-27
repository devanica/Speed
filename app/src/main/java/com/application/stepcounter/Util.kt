package com.application.stepcounter

import java.text.DecimalFormat

object Util {
    private val decimalFormat = DecimalFormat("#.##")

    fun calculateKilometers(steps: Int): String{
        return decimalFormat.format((steps.toDouble()/2000)*1.609)
    }

    fun calculateCalories(steps: Int): String{
        return decimalFormat.format((steps.toDouble()/2000)*1.609*55)
    }

    fun addYAxisValuesToArray(yAxisValues: ArrayList<Float>, f: Float) {
        yAxisValues.add(f)
    }
}