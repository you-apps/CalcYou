package net.youapps.calcyou.data.converters

class TemperatureConverter : UnitConverter {
    override val units: List<ConverterUnit> = listOf(
        object : ConverterUnit {
            override val name: String = "Celsius"

            override fun convertFrom(value: Double): Double {
                return value
            }

            override fun convertTo(value: Double): Double {
                return value
            }
        },
        object : ConverterUnit {
            override val name: String = "Fahrenheit"

            override fun convertFrom(value: Double): Double {
                return (value - 32) / 1.8
            }

            override fun convertTo(value: Double): Double {
                return (value * 1.8) + 32
            }
        },
        object : ConverterUnit {
            override val name: String = "Kelvin"

            override fun convertFrom(value: Double): Double {
                return value - 273.15
            }

            override fun convertTo(value: Double): Double {
                return value + 273.15
            }
        },
    )
}