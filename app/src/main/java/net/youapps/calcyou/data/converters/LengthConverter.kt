package net.youapps.calcyou.data.converters

import net.youapps.calcyou.R

class LengthConverter : UnitConverter {
    override val units: List<ConverterUnit> = listOf(
        FactorUnit(R.string.meter, 1.0),
        FactorUnit(R.string.kilometer, 1E3),
        FactorUnit(R.string.centimeter, 1E-2),
        FactorUnit(R.string.millimeter, 1E-3),
        FactorUnit(R.string.mile, 1609.344),
        FactorUnit(R.string.yard, 0.9144),
        FactorUnit(R.string.foot, 0.3048),
        FactorUnit(R.string.inch, 0.0254),
        FactorUnit(R.string.nautical_mile, 1852)
    )
}
