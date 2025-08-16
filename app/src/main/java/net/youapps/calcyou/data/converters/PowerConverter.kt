package net.youapps.calcyou.data.converters

import net.youapps.calcyou.R

class PowerConverter : UnitConverter<Double> {
    override val units: List<ConverterUnit<Double>> = listOf(
        FactorUnit(R.string.watt, 1.0),
        FactorUnit(R.string.kilowatt, 1E3),
        FactorUnit(R.string.megawatt, 1E6),
        FactorUnit(R.string.horsepower, 745.7),
        FactorUnit(R.string.btu_per_hour, 252.0)
    )
}