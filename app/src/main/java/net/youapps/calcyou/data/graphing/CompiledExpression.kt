/*
 This code is a modified version of https://github.com/danielgindi/KotlinEval
 The original source code is licensed under MIT LICENSE
 */

package net.youapps.calcyou.data.graphing

class CompiledExpression internal constructor(
    internal var root: Token,
    var configuration: EvalConfiguration
) {
    fun execute(): Double? =
        Evaluator.execute(expression = this)

    fun execute(constant: Pair<String, Double>): Double? =
        Evaluator.execute(this, constant)

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