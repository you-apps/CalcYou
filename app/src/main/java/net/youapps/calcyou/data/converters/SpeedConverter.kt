package net.youapps.calcyou.data.converters

class SpeedConverter : UnitConverter {
    override val units: List<ConverterUnit> = listOf(
        FactorUnit("meter per second", 1.0),
        FactorUnit("kilometer per hour", 0.277777778),
        FactorUnit("mile per hour", 0.44704),
        FactorUnit("knot", 0.514444444),
        FactorUnit("feet per second", 0.3048)
    )
}