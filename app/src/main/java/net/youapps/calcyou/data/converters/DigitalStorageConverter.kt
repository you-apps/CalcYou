package net.youapps.calcyou.data.converters

class DigitalStorageConverter : UnitConverter {
    override val units: List<ConverterUnit> = listOf(
        FactorUnit("bit", 1.0),
        FactorUnit("byte", 8.0),
        FactorUnit("kilobyte", 8.0 * 1024),
        FactorUnit("megabyte", 8.0 * 1024 * 1024),
        FactorUnit("gigabyte", 8.0 * 1024 * 1024 * 1024),
        FactorUnit("terabyte", 8.0 * 1024 * 1024 * 1024 * 1024),
        FactorUnit("petabyte", 8.0 * 1024 * 1024 * 1024 * 1024 * 1024),
        FactorUnit("exabyte", 8.0 * 1024 * 1024 * 1024 * 1024 * 1024)
    )
}