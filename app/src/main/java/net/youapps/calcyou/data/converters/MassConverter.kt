package net.youapps.calcyou.data.converters

class MassConverter : UnitConverter {
    override val units: List<ConverterUnit> = listOf(
        FactorUnit("kilogram", 1.0),
        FactorUnit("gram", 1E-3),
        FactorUnit("milligram", 1E-6),
        FactorUnit("tonne", 1E3),
        FactorUnit("pound", 0.45359237),
        FactorUnit("ounce", 0.028349523125)
    )
}