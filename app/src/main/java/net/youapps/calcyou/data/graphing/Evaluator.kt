/*
 This code is a modified version of https://github.com/danielgindi/KotlinEval
 The original source code is licensed under MIT LICENSE
 */

package net.youapps.calcyou.data.graphing

import java.text.ParseException
import kotlin.math.pow

object Evaluator {
    private val configuration = EvalConfiguration()

    fun compile(expression: String): CompiledExpression {
        val tokens =
            tokenizeExpression(expression = expression)
        var end = tokens.size

        // Collapse +-
        var i = 1
        while (i < end) {
            val token = tokens[i]
            val prevToken = tokens[i - 1]
            // When there are two consecetive + or - tokens, simplify them to one
            // For example 5--1 becomes 5+1
            if (token is Token.Op && (token.value == Operator.SUB || token.value == Operator.ADD) && prevToken is Token.Op && (prevToken.value == Operator.SUB || prevToken.value == Operator.ADD)) {
                if (prevToken.value != Operator.ADD) {
                    if ((token.value == Operator.SUB)) {
                        token.value = Operator.ADD
                    } else {
                        token.value = Operator.SUB
                    }
                }
                tokens.removeAt(i - 1)
                end = tokens.size
                continue
            }

            // When we have something like this: "5*-1", we will move the "-" to be part of the number token.
            if (token is Token.Number &&
                prevToken is Token.Op &&
                (prevToken.value == Operator.SUB || prevToken.value == Operator.ADD) &&
                ((i > 1 && tokens[i - 2] is Token.Op) || i == 1)
            ) {
                if (prevToken.value == Operator.SUB) {
                    token.value = token.value * -1
                }
                tokens.removeAt(i - 1)
                end = tokens.size
                continue
            }
            i += 1
        }

        // Take care of groups (including function calls)
        i = 0
        while (i < end) {
            val token = tokens[i]
            if (token is Token.LeftParen) {
                groupTokens(tokens = tokens, startAt = i)
                end = tokens.size
                continue
            }
            i += 1
        }

        // Build the tree
        val tree = buildTree(tokens = tokens, configuration = configuration)

        return CompiledExpression(root = tree, configuration = configuration)
    }

    fun execute(expression: String): Any? =
        execute(expression = compile(expression = expression))

    fun execute(expression: CompiledExpression): Double? =
        evaluateToken(token = expression.root, configuration = expression.configuration)

    fun execute(expression: CompiledExpression, constants: List<Pair<String, Double>>): Double? {
        configuration.clearConstants()
        for (constant in constants) {
            configuration.setConstant(constant.first, constant.second)
        }
        return evaluateToken(token = expression.root, configuration = configuration)
    }

    private fun opAtPosition(
        expression: String,
        start: Int
    ): Operator? {
        var op: Operator? = null
        val allOperators = configuration.allOperators
        for (item in allOperators) {
            if (op != null && (op == item || item.length <= op.length)) {
                continue
            }
            if (expression.substring(
                    startIndex = start,
                    endIndex = start + item.length
                ) == item.text
            ) {
                op = item
            }
        }
        return op
    }

    private fun indexOfOpInTokens(tokens: List<Token>, op: Operator): Int? {
        for ((i, token) in tokens.withIndex()) {
            if (token is Token.Op && token.value == op) {
                return i
            }
        }
        return null
    }

    private fun lastIndexOfOpInTokens(tokens: List<Token>, op: Operator): Int? {
        for (i in tokens.size - 1 downTo 0) {
            val token = tokens[i]
            if (token is Token.Op && token.value == op) {
                return i
            }
        }
        return null
    }

    private data class MatchWithIndex(val index: Int, val match: String)

    private fun lastIndexOfOpArray(
        tokens: List<Token>,
        ops: List<Operator>
    ): MatchWithIndex? {
        var pos: Int? = null
        var bestMatch: String? = null

        for (item in ops) {
            val opIndex = (if (item == Operator.POW) {
                indexOfOpInTokens(tokens = tokens, op = item)
            } else {
                lastIndexOfOpInTokens(tokens = tokens, op = item)
            }) ?: continue

            if (pos == null || opIndex > pos) {
                pos = opIndex
                bestMatch = item.text
            }
        }

        if (pos == null || bestMatch == null)
            return null

        return MatchWithIndex(index = pos, match = bestMatch)
    }

    /*** Parses a number from the givenstring, starting at the specified index.
     *
     * @param data The string to parse.
     * @param startAt The starting index in the string.
     * @return A [MatchWithIndex] object containing the parsed number and the index after the parsed number.
     * @throws ParseException If a number cannotbe parsed.
     */
    private fun parseNumber(data: String, startAt: Int): MatchWithIndex {
        var i = startAt
        val endIndex = data.length
        var exp =
            0 // 0 - no expoent found | 1 - exponent found | 2 - exponent with a sign found | 3 - exponent found with a preceding number
        var isDecimalNumber = false
        if (i >= endIndex) {
            throw ParseException("Can't parse token at $i", -1)
        }
        while (i < endIndex) {
            val c = data[i]
            if ((c in '0'..'9')) { // Numeric
                if (exp == 1 || exp == 2) {
                    exp = 3 // There are numbers after the exponent and the sign
                }
            } else if ((c == '.')) { // Decimal Point
                if (isDecimalNumber || exp > 0) {
                    break
                }
                isDecimalNumber = true
            } else if ((c == 'e')) {
                if (exp > 0) {
                    break  // Came across a second 'e'. number has probably reached the end
                }
                exp = 1 // Exponent
            } else if ((exp == 1 && (c == '-' || c == '+'))) {
                exp = 2 // Exponent with a sign
            } else {
                break
            }
            i++
        }
        if ((i == startAt || exp == 1 /* Exponent 'e' without number */ || exp == 2 /* Exponent 'e' and sign without number */)) {
            throw ParseException("Unexpected character at index $i", -1)
        }

        return MatchWithIndex(
            index = i,
            match = data.substring(startIndex = startAt, endIndex = i)
        )
    }

    private fun tokenizeExpression(
        expression: String
    ): MutableList<Token> {
        val varNameChars = configuration.varNameChars
        val tokens = mutableListOf<Token>()

        if (expression.isEmpty())
            return tokens

        var i = 0
        val endIndex = expression.length
        while (i < endIndex) {
            var c = expression[i]

            val isDigit = c in '0'..'9'

            if (isDigit || c == '.') {
                // Starting a number
                val parsedNumber = parseNumber(data = expression, startAt = i)
                tokens.add(Token.Number(position = i, value = parsedNumber.match.toDouble()))
                i = parsedNumber.index
                continue
            }

            var isVarChars = varNameChars.contains(c)
            if (isVarChars) {
                // Starting a variable name - can start only with A-Z_
                var token = ""
                while (i < endIndex) {
                    c = expression[i]
                    isVarChars = varNameChars.contains(c)
                    if (!isVarChars) {
                        break
                    }
                    token += c
                    i++
                }
                tokens.add(
                    Token.Var(
                        position = i - token.length,
                        value = token
                    )
                )
                continue
            }

            if (c == '(') {
                tokens.add(Token.LeftParen(position = i))
                i++
                continue
            }

            if (c == ')') {
                tokens.add(Token.RightParen(position = i))
                i++
                continue
            }

            if (c == ',') { // Commas are used to seperate function arguments
                tokens.add(Token.Comma(position = i))
                i++
                continue
            }
            if (c == ' ' || c == '\t' || c == '\u000c' || c == '\r' || c == '\n') {
                // Whitespace, skip
                i++
                continue
            }

            val op = opAtPosition(expression = expression, start = i)
            if (op != null) {
                tokens.add(Token.Op(position = i, value = op))
                i += op.length
                continue
            }

            throw ParseException("Unexpected token at index $i", -1)
        }
        return tokens
    }

    private fun groupTokens(tokens: MutableList<Token>, startAt: Int = 0): Token {
        val isFunc = startAt > 0 && tokens[startAt - 1] is Token.Var
        var rootToken = tokens[if (isFunc) startAt - 1 else startAt]

        var groups: MutableList<MutableList<Token>>? = null
        var sub: MutableList<Token> = mutableListOf()
        if (isFunc) {
            groups = mutableListOf()
            val newToken = Token.Call(
                rootToken.position,
                argumentsGroups = groups,
                function = getFunFromName((rootToken as Token.Var).value)
            )
            tokens[startAt - 1] = newToken
            rootToken = newToken
        } else {
            val newToken = Token.Group(rootToken.position, tokens = sub)
            tokens[startAt] = newToken
            rootToken = newToken
        }

        var i = startAt + 1
        var end = tokens.size

        while (i < end) {
            val token = tokens[i]

            if (isFunc && token is Token.Comma) {
                sub = mutableListOf()
                groups!!.add(sub)
                i += 1
                continue
            }

            if (token is Token.RightParen) {
                if (isFunc) {
                    for (r in i downTo startAt)
                        tokens.removeAt(r)
                } else {
                    for (r in i downTo (startAt + 1))
                        tokens.removeAt(r)
                }
                return rootToken
            }

            if (token is Token.LeftParen) {
                groupTokens(tokens = tokens, startAt = i)
                end = tokens.size
                continue
            }

            if (isFunc && groups!!.size == 0) {
                groups.add(sub)
            }

            sub.add(token)
            i += 1
        }

        throw ParseException(
            "Unmatched parenthesis for parenthesis at index $startAt",
            -1
        )
    }

    private fun buildTree(tokens: MutableList<Token>, configuration: EvalConfiguration): Token {
        val order = configuration.operatorOrder
        val orderCount = order.size
        var i = orderCount - 1
        while (i >= 0) {
            val cs = order[i]
            val match = lastIndexOfOpArray(tokens = tokens, ops = cs)
            if (match == null) {
                i -= 1
                continue
            }

            val pos = match.index
            val op = match.match

            val token = tokens[pos] as Token.Op
            var left: MutableList<Token>? = tokens.subList(0, pos).toMutableList()
            val right: MutableList<Token> = tokens.subList(pos + 1, tokens.size)

            if (left?.size == 0 && (op == "-" || op == "+")) {
                left = null
            }
            if ((left != null && left.size == 0) || right.size == 0) {
                throw ParseException("Invalid expression, missing operand at $pos", -1)
            }
            if (left == null && op == "-") {
                left = mutableListOf()
                left.add(Token.Number(value = 0.0))
            } else if (left == null) {
                return buildTree(tokens = right, configuration = configuration)
            }
            token.left = buildTree(tokens = left, configuration = configuration)
            token.right = buildTree(tokens = right, configuration = configuration)
            return token
        }

        if (tokens.size > 1) {
            throw ParseException(
                "Invalid expression, missing operand or operator at ${tokens[1].position}",
                -1
            )
        }

        if (tokens.size == 0) {
            throw ParseException("Invalid expression, missing operand or operator.", -1)
        }

        // Recursive function reached a single token level
        var singleToken = tokens[0]

        when (singleToken) {
            is Token.Group -> {
                singleToken = buildTree(
                    tokens = singleToken.tokens,
                    configuration = configuration
                )
            }

            is Token.Call -> {
                singleToken.arguments = mutableListOf()
                for (a in 0 until (singleToken.argumentsGroups?.size ?: 0)) {
                    if (singleToken.argumentsGroups!![a].size == 0) {
                        singleToken.arguments?.add(null)
                    } else {
                        singleToken.arguments?.add(
                            buildTree(
                                tokens = singleToken.argumentsGroups!![a],
                                configuration = configuration
                            )
                        )
                    }
                }
            }

            is Token.Comma -> {
                throw ParseException(
                    "Unexpected character at index ${singleToken.position}",
                    -1
                )
            }

            else -> {
            }
        }

        return singleToken
    }

    private fun evaluateToken(token: Token, configuration: EvalConfiguration): Double? {
        when (token) {
            is Token.Number -> {
                return token.value
            }

            is Token.Var -> {
                val tokenValue = token.value
                val constants = configuration.constants
                if (constants != null) {
                    if (constants.containsKey(tokenValue))
                        return constants[tokenValue]!!

                    if (constants.containsKey(tokenValue.uppercase()))
                        return constants[tokenValue.uppercase()]!!
                }

                if (configuration.genericConstants.containsKey(tokenValue))
                    return configuration.genericConstants[tokenValue]!!

                if (configuration.genericConstants.containsKey(tokenValue.uppercase()))
                    return configuration.genericConstants[tokenValue.uppercase()]!!

                throw ParseException("Error Evaluating token: Variable ${tokenValue} invalid", -1)
            }

            is Token.Call -> return evaluateFunction(token = token, configuration = configuration)
            is Token.Op -> {
                val left = token.left
                val right = token.right
                if (left == null || right == null) {
                    throw ParseException(
                        "An unexpected error occurred while evaluating expression",
                        -1
                    )
                }

                val a = evaluateToken(left, configuration)
                    ?: throw ParseException("Error Evaluating token: Left operand invalid", -1)
                val b = evaluateToken(right, configuration)
                    ?: throw ParseException("Error Evaluating token: Right operand invalid", -1)

                when (token.value) {
                    Operator.DIV -> // Divide
                        return a.div(b)

                    Operator.MUL -> // Multiply
                        return a.times(b)

                    Operator.ADD -> // Add
                        return a.plus(b)

                    Operator.SUB -> // Subtract
                        return a.minus(b)

                    Operator.POW -> // Power
                        return a.pow(b)

                    Operator.MOD -> // Mod
                        return a.mod(b)
                }

            }

            else -> {
            }
        }

        throw ParseException("An unexpected error occurred while evaluating expression", -1)
    }

    private fun evaluateFunction(token: Token.Call, configuration: EvalConfiguration): Double {
        val args = mutableListOf<Double>()
        for (arg in token.arguments ?: listOf()) {
            if (arg != null) {
                val value = evaluateToken(token = arg, configuration = configuration)
                    ?: throw ParseException(
                        "An unexpected error occurred while evaluating expression",
                        -1
                    )
                args.add(value)
            }
        }

        return token.function.invoke(args)
    }

    private fun getFunFromName(fname: String): EvalFunctionBlock {
        val fnameUpper = fname.uppercase()

        val functions = configuration.functions
        if (functions != null) {
            functions[fname]?.let {
                return it
            }

            functions[fnameUpper]?.let {
                return it
            }
        }
        configuration.genericFunctions[fname]?.let {
            return it
        }

        configuration.genericFunctions[fnameUpper]?.let {
            return it
        }
        throw ParseException("Function named \"${fname}\" was not found", -1)
    }
}