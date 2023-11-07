package net.youapps.calcyou.data.converters

import net.youapps.calcyou.R

class SpeedConverter : UnitConverter {
    override val units: List<ConverterUnit> = listOf(
        FactorUnit(R.string.meter_per_second, 1.0),
        FactorUnit(R.string.kilometer_per_hour, 0.277777778),
        FactorUnit(R.string.mile_per_hour, 0.44704),
        FactorUnit(R.string.knot, 0.514444444),
        FactorUnit(R.string.feet_per_second, 0.3048)
    )
}