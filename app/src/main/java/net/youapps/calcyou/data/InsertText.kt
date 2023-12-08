package net.youapps.calcyou.data

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.getSelectedText
import androidx.compose.ui.text.input.getTextAfterSelection
import androidx.compose.ui.text.input.getTextBeforeSelection

fun TextFieldValue.insertText(insertText: String): TextFieldValue {
    val maxChars = text.length
    val textBeforeSelection = getTextBeforeSelection(maxChars)
    val textAfterSelection = getTextAfterSelection(maxChars)
    val newText = "$textBeforeSelection$insertText$textAfterSelection"
    val newCursorPosition = textBeforeSelection.length + insertText.length
    return TextFieldValue(
        text = newText,
        selection = TextRange(newCursorPosition)
    )
}

fun TextFieldValue.backSpace(): TextFieldValue {
    val maxChars = text.length
    val textBeforeSelection = getTextBeforeSelection(maxChars)
    val textAfterSelection = getTextAfterSelection(maxChars)
    val selectedText = getSelectedText()
    if (textBeforeSelection.isEmpty() && selectedText.isEmpty()) return this
    val newText =
        if (selectedText.isEmpty()) {
            "${textBeforeSelection.dropLast(1)}$textAfterSelection"
        } else {
            "$textBeforeSelection$textAfterSelection"
        }
    val newCursorPosition = textBeforeSelection.length - if (selectedText.isEmpty()) 1 else 0
    return TextFieldValue(
        text = newText,
        selection = TextRange(newCursorPosition)
    )
}
