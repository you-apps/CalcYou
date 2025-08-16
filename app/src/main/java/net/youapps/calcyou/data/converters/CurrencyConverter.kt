package net.youapps.calcyou.data.converters

import net.youapps.calcyou.CalculatorApplication
import net.youapps.calcyou.R
import net.youapps.calcyou.data.Either

data class Currency(
    val name: String,
    val exchangeRate: Double,
)

class CurrencyConverter(): UnitConverter<Double> {
    val currencies: List<Currency> by lazy {
        CalculatorApplication.applicationContext.resources.openRawResource(R.raw.currencies)
            .bufferedReader()
            .readLines()
            .filter { it.isNotBlank() }
            .map { line ->
                val parts = line.split(",", limit = 2)
                Currency(parts[0], parts[1].toDouble())
            }
    }

    override val units: List<ConverterUnit<Double>> by lazy {
        currencies.map {
            object : ConverterUnit<Double> {
                override val name: Either<Int, String> = Either.Right(it.name)

                override fun convertFrom(value: Double): Double {
                    return value / it.exchangeRate
                }

                override fun convertTo(value: Double): Double {
                    return value * it.exchangeRate
                }
            }
        }
    }
}