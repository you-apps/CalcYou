package net.youapps.calcyou.data.converters

class AreaConverter : UnitConverter {
    override val units: List<ConverterUnit> = listOf(
        FactorUnit("square meter", 1.0),
        FactorUnit("square kilometer", 1E6),
        FactorUnit("square centimeter", 1E-4),
        FactorUnit("square millimeter", 1E-7),
        FactorUnit("hectare", 1E4),
        FactorUnit("acre", 4046.8564224),
        FactorUnit("square yard", 0.83612736),
        FactorUnit("square foot", 0.09290304)
    )
}