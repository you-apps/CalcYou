package net.youapps.calcyou.ui.components

import android.view.SoundEffectConstants
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Calculate
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import net.youapps.calcyou.Destination
import net.youapps.calcyou.R


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavDrawerContent(
    currentDestination: Destination,
    onDestinationSelected: (Destination) -> Unit
) {
    val view = LocalView.current
    ModalDrawerSheet(modifier = Modifier.width(250.dp)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
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
                        contentDescription = null
                    )
                    Text(
                        stringResource(id = R.string.app_name),
                        color = MaterialTheme.colorScheme.primary,
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
            stickyHeader {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(50))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        text = "Converters",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
            items(items = Destination.Converter.values) {
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            imageVector = it.icon,
                            contentDescription = null
                        )
                    },
                    label = { Text(text = stringResource(id = it.resId)) },
                    selected = it == currentDestination,
                    onClick = {
                        view.playSoundEffect(SoundEffectConstants.CLICK)
                        onDestinationSelected(it)
                    }
                )
            }
        }
    }
}
