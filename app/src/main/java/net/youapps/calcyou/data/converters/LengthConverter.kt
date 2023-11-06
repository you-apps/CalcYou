package net.youapps.calcyou.data.converters

class LengthConverter : UnitConverter {
    override val units: List<ConverterUnit> = listOf(
        FactorUnit("meter", 1.0),
        FactorUnit("kilometer", 1E3),
        FactorUnit("centimeter", 1E-2),
        FactorUnit("millimeter", 1E-3),
        FactorUnit("mile", 1609.344),
        FactorUnit("yard", 0.9144),
        FactorUnit("foot", 0.3048),
        FactorUnit("inch", 0.0254)
    )
}