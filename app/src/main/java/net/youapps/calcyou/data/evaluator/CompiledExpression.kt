package net.youapps.calcyou.data.evaluator

class CompiledExpression internal constructor(
    internal var root: Token,
    var configuration: EvalConfiguration
) {
    fun execute(): Double? =
        ExpressionEvaluator.execute(expression = this)

    fun execute(constants: List<Pair<String, Double>>): Double? =
        ExpressionEvaluator.execute(this, constants)

    fun setConstant(name: String, value: Double) {
        configuration.setConstant(name = name, value = value)
    }

    fun removeConstant(name: String) {
        configuration.removeConstant(name = name)
    }

    fun setFunction(name: String, func: EvalFunctionBlock) {
        configuration.setFunction(name = name, func = func)
    }

    fun removeFunction(name: String) {
        configuration.removeFunction(name = name)
    }

    fun clearConstants() {
        configuration.clearConstants()
    }

    fun clearFunctions() {
        configuration.clearConstants()
    }
}