package net.youapps.calcyou.data.converters


interface ConverterUnit {
    val name: String
    fun convertFrom(value: Double): Double
    fun convertTo(value: Double): Double
    fun convert(outputUnit: ConverterUnit, value: Double): Double {
        return outputUnit.convertTo(this.convertFrom(value))
    }
}