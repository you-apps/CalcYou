package net.youapps.calcyou.ui.components.buttons

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import net.youapps.calcyou.R
import net.youapps.calcyou.data.KeyMap

@Composable
fun KeyboardPanel(onKeyPress: (String) -> Unit, modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    Column(modifier = modifier.verticalScroll(scrollState)) {
        KeyRow(title = stringResource(R.string.numbers), items = KeyMap.numbers, onKeyPress)
        KeyRow(title = stringResource(R.string.fractions), items = KeyMap.fractions, onKeyPress)
        KeyRow(title = stringResource(R.string.simple_math), items = KeyMap.simpleMath, onKeyPress)
        KeyRow(
            title = stringResource(R.string.common_symbols),
            items = KeyMap.scienceSymbols,
            onKeyPress
        )
        KeyRow(
            title = stringResource(R.string.superscript),
            items = KeyMap.superscriptNumbers,
            onKeyPress
        )
        KeyRow(
            title = stringResource(R.string.subscript),
            items = KeyMap.subscriptNumbers,
            onKeyPress
        )
        KeyRow(
            title = stringResource(R.string.english_uppercase),
            items = KeyMap.englishUppercaseLetters,
            onKeyPress
        )
        KeyRow(
            title = stringResource(R.string.english_lowercase),
            items = KeyMap.englishLowercaseLetters,
            onKeyPress
        )
        KeyRow(
            title = stringResource(R.string.greek_uppercase),
            items = KeyMap.greekUppercaseLetters,
            onKeyPress
        )
        KeyRow(
            title = stringResource(R.string.greek_lowercase),
            items = KeyMap.greekLowercaseLetters,
            onKeyPress
        )
        KeyRow(title = stringResource(R.string.arrows), items = KeyMap.arrows, onKeyPress)
    }
}
