package net.youapps.calcyou.data.converters

import androidx.annotation.StringRes

class FactorUnit(@StringRes override val name: Int, val conversionFactor: Double) : ConverterUnit {
    override fun convertFrom(value: Double): Double {
        return value * conversionFactor
    }

    override fun convertTo(value: Double): Double {
        return value / conversionFactor
    }
}
