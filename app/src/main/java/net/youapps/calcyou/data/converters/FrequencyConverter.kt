package net.youapps.calcyou.data.converters

class FrequencyConverter : UnitConverter {
    override val units: List<ConverterUnit> = listOf(
        FactorUnit("hertz", 1.0),
        FactorUnit("kilohertz", 1E3),
        FactorUnit("megahertz", 1E6),
        FactorUnit("gigahertz", 1E9)
    )
}