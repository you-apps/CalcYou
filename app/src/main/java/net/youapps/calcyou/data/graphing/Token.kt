package net.youapps.calcyou.data.graphing

sealed class Token(val position: Int?) {
    class Group(
        position: Int?,
        val tokens: MutableList<Token>
    ) : Token(position = position)

    class Call(
        position: Int? = null,
        var argumentsGroups: MutableList<MutableList<Token>>? = null,
        var arguments: MutableList<Token?>? = null,
        var function: EvalFunctionBlock
    ) : Token(position)

    class Var(
        position: Int? = null,
        var value: String
    ) : Token(position)

    class Number(
        position: Int? = null,
        var value: Double
    ) : Token(position)

    class Op(
        position: Int? = null,
        var value: Operator
    ) : Token(position) {
        var left: Token? = null
        var right: Token? = null
    }

    class LeftParen(
        position: Int? = null
    ) : Token(position)

    class RightParen(
        position: Int? = null
    ) : Token(position)

    class Comma(
        position: Int? = null
    ) : Token(position)
}

enum class Operator(val text: String) {
    ADD("+"),
    SUB("-"),
    MUL("*"),
    DIV("/"),
    POW("**"),
    MOD("%")
}

internal fun String.toOperator(): Operator = when (this) {
    "+" -> Operator.ADD
    "-" -> Operator.SUB
    "*" -> Operator.MUL
    "/" -> Operator.DIV
    "**" -> Operator.POW
    "%" -> Operator.MOD
    else -> throw IllegalArgumentException("Unknown operator: $this")
}

internal val Operator.length: Int
    get() = when (this) {
        Operator.POW -> 2
        else -> 1
    }