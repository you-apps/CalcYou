package net.youapps.calcyou.ui.components.buttons

import android.view.SoundEffectConstants
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp

@Composable
fun RowScope.CalculatorButton(
    text: String,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
    aspectRatio: Float = 1f,
    onClick: () -> Unit,
    onLongClick: () -> Unit = { },
) {
    CalculatorButton(
        backgroundColor = backgroundColor,
        aspectRatio = aspectRatio,
        onClick = onClick,
        onLongClick = onLongClick
    ) {
        Text(
            text = text,
            color = textColor,
            style = MaterialTheme.typography.displaySmall
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RowScope.CalculatorButton(
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    aspectRatio: Float = 1f,
    onClick: () -> Unit,
    onLongClick: () -> Unit = { },
    content: @Composable (BoxScope.() -> Unit)
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
            .background(backgroundColor)
            .aspectRatio(aspectRatio)
            .weight(aspectRatio),
        content = content
    )
}
