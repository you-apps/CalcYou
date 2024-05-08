package net.youapps.calcyou.ui.components

import android.view.SoundEffectConstants
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.outlined.KeyboardAlt
import androidx.compose.material.icons.outlined.LineAxis
import androidx.compose.material.icons.outlined.WifiProtectedSetup
import androidx.compose.material.icons.rounded.Calculate
import androidx.compose.material.icons.rounded.KeyboardAlt
import androidx.compose.material.icons.rounded.LineAxis
import androidx.compose.material.icons.rounded.WifiProtectedSetup
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import net.youapps.calcyou.Destination
import net.youapps.calcyou.R


@Composable
fun NavDrawerContent(
    currentDestination: Destination,
    onDestinationSelected: (Destination) -> Unit
) {
    val view = LocalView.current
    ModalDrawerSheet(modifier = Modifier.width(250.dp)) {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)

        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        modifier = Modifier.size(128.dp),
                        painter = painterResource(id = R.drawable.ic_launcher_monochrome),
                        contentDescription = null,
                        tint = LocalContentColor.current
                    )
                    Text(
                        stringResource(id = R.string.app_name),
                        color = LocalContentColor.current,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
            item {
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Rounded.Calculate,
                            contentDescription = null
                        )
                    },
                    label = { Text(text = stringResource(id = R.string.calculator)) },
                    selected = Destination.Calculator == currentDestination,
                    onClick = {
                        view.playSoundEffect(SoundEffectConstants.CLICK)
                        onDestinationSelected(Destination.Calculator)
                    }
                )
            }
            item {
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Rounded.WifiProtectedSetup,
                            contentDescription = null
                        )
                    },
                    label = { Text(text = stringResource(id = R.string.converter)) },
                    selected = Destination.Converters == currentDestination,
                    onClick = {
                        view.playSoundEffect(SoundEffectConstants.CLICK)
                        onDestinationSelected(Destination.Converters)
                    }
                )
            }
            item {
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Rounded.KeyboardAlt,
                            contentDescription = null
                        )
                    },
                    label = { Text(text = stringResource(id = R.string.character_input)) },
                    selected = Destination.CharacterInput == currentDestination,
                    onClick = {
                        view.playSoundEffect(SoundEffectConstants.CLICK)
                        onDestinationSelected(Destination.CharacterInput)
                    }
                )
            }
            item {
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Rounded.LineAxis,
                            contentDescription = null
                        )
                    },
                    label = { Text(text = stringResource(id = R.string.graphing)) },
                    selected = Destination.Graphing == currentDestination,
                    onClick = {
                        view.playSoundEffect(SoundEffectConstants.CLICK)
                        onDestinationSelected(Destination.Graphing)
                    }
                )
            }
        }
    }
}

@Composable
fun ColumnScope.NavRailContent(
    currentDestination: Destination,
    onDestinationSelected: (Destination) -> Unit
) {
    val view = LocalView.current

    NavigationRailItem(
        alwaysShowLabel = false,
        icon = {
            Icon(
                imageVector = Icons.Outlined.Calculate,
                contentDescription = null
            )
        },
        label = { Text(text = stringResource(id = R.string.calculator)) },
        selected = Destination.Calculator == currentDestination,
        onClick = {
            view.playSoundEffect(SoundEffectConstants.CLICK)
            onDestinationSelected(Destination.Calculator)
        }
    )
    NavigationRailItem(
        alwaysShowLabel = false,
        icon = {
            Icon(
                imageVector = Icons.Outlined.WifiProtectedSetup,
                contentDescription = null
            )
        },
        label = {
            Text(
                text = stringResource(id = R.string.converter),
                textAlign = TextAlign.Center
            )
        },
        selected = Destination.Converters == currentDestination,
        onClick = {
            view.playSoundEffect(SoundEffectConstants.CLICK)
            onDestinationSelected(Destination.Converters)
        }
    )
    NavigationRailItem(
        alwaysShowLabel = false,
        icon = {
            Icon(
                imageVector = Icons.Outlined.KeyboardAlt,
                contentDescription = null
            )
        },
        label = {
            Text(
                text = stringResource(id = R.string.character_input),
                textAlign = TextAlign.Center
            )
        },
        selected = Destination.CharacterInput == currentDestination,
        onClick = {
            view.playSoundEffect(SoundEffectConstants.CLICK)
            onDestinationSelected(Destination.CharacterInput)
        }
    )
    NavigationRailItem(
        alwaysShowLabel = false,
        icon = {
            Icon(
                imageVector = Icons.Outlined.LineAxis,
                contentDescription = null
            )
        },
        label = {
            Text(
                text = stringResource(id = R.string.graphing),
                textAlign = TextAlign.Center
            )
        },
        selected = Destination.Graphing == currentDestination,
        onClick = {
            view.playSoundEffect(SoundEffectConstants.CLICK)
            onDestinationSelected(Destination.Graphing)
        }
    )

}
