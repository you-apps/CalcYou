/*
 This code is a modified version of https://github.com/danielgindi/KotlinEval
 The original source code is licensed under MIT LICENSE
 */

package net.youapps.calcyou.data.evaluator

typealias EvalFunctionBlock = (args: List<Double>) -> Double

class EvalConfiguration(
    var constants: MutableMap<String, Double>? = null,
    var functions: MutableMap<String, EvalFunctionBlock>? = null
) {

    internal var allOperators = listOf<Operator>()
    private var _operatorOrder = listOf<List<Operator>>()
    var operatorOrder: List<List<Operator>>
        get() {
            return _operatorOrder
        }
        set(newValue) {
            val ops = mutableListOf<Operator>()
            for (ops2 in newValue)
                ops.addAll(ops2)

            _operatorOrder = newValue
            allOperators = ops
        }

    var rightAssociativeOps = setOf<String>()

    var varNameChars = setOf<Char>()

    var genericConstants = mutableMapOf<String, Double>()
    var genericFunctions = mutableMapOf<String, EvalFunctionBlock>()

    @Suppress("unused")
    fun setConstant(name: String, value: Double) {
        if (constants == null) {
            constants = mutableMapOf()
        }
        constants!![name] = value
    }

    @Suppress("unused")
    fun removeConstant(name: String) {
        if (constants == null) {
            return
        }
        constants?.remove(name)
    }

    @Suppress("unused")
    fun setFunction(name: String, func: EvalFunctionBlock) {
        if (functions == null) {
            functions = mutableMapOf()
        }
        functions!![name] = func
    }

    @Suppress("unused")
    fun removeFunction(name: String) {
        if (functions == null) {
            return
        }
        functions?.remove(name)
    }

    @Suppress("unused")
    fun clearConstants() {
        constants?.clear()
    }

    @Suppress("unused")
    fun clearFunctions() {
        functions?.clear()
    }

    init {
        this.operatorOrder = Defaults.defaultOperatorOrder
        this.rightAssociativeOps = Defaults.defaultRightAssociativeOps
        this.varNameChars = Defaults.defaultVarNameChars
        this.genericConstants = Defaults.defaultGenericConstants.toMutableMap()
        this.genericFunctions = Defaults.getDefaultGenericFunctions().toMutableMap()
    }
}