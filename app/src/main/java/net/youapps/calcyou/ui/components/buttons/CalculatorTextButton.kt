package net.youapps.calcyou.ui.components.buttons

import android.view.SoundEffectConstants
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RowScope.CalculatorTextButton(
    text: String,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    square: Boolean = true,
    textStyle: TextStyle = MaterialTheme.typography.displaySmall,
    onClick: () -> Unit,
    onLongClick: () -> Unit = { }
) {
    val view = LocalView.current
    val haptic = LocalHapticFeedback.current
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(CircleShape)
            .combinedClickable(
                onClick = {
                    onClick.invoke()
                    view.playSoundEffect(SoundEffectConstants.CLICK)
                }, onLongClick = {
                    onLongClick.invoke()
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                }
            )
            .weight(1f)
            .let {
                if (square) it.aspectRatio(1f) else it
            }
    ) {
        Text(
            text = text,
            color = textColor,
            style = textStyle
        )
    }
}