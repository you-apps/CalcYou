package net.youapps.calcyou.data.converters

class FactorUnit(override val name: String, val conversionFactor: Double) : ConverterUnit {
    override fun convertFrom(value: Double): Double {
        return value * conversionFactor
    }

    override fun convertTo(value: Double): Double {
        return value / conversionFactor
    }

    override fun convert(outputUnit: ConverterUnit, value: Double): Double {
        return outputUnit.convertTo(this.convertFrom(value))
    }
}
