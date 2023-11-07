package net.youapps.calcyou.data.converters

class FactorUnit(override val name: String, val conversionFactor: Double) : ConverterUnit {
    override fun convertFrom(value: Double): Double {
        return value * conversionFactor
    }

    override fun convertTo(value: Double): Double {
        return value / conversionFactor
    }
}
