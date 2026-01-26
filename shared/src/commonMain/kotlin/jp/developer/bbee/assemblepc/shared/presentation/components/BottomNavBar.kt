package jp.developer.bbee.assemblepc.shared.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import assemblepc.shared.generated.resources.Res
import assemblepc.shared.generated.resources.navigation_icon_description
import jp.developer.bbee.assemblepc.shared.presentation.ROUTE_LIST
import jp.developer.bbee.assemblepc.shared.presentation.ScreenRoute
import jp.developer.bbee.assemblepc.shared.presentation.theme.AssemblePCTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun BottomNavBar(
    currentRoute: ScreenRoute?,
    enabled: Boolean,
    navigateTo: (ScreenRoute) -> Unit,
) {
    BottomNavigation(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.surface,
    ) {
        ROUTE_LIST.forEach { screenRoute ->
            val selected = currentRoute == screenRoute
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = screenRoute.getIcon(),
                        contentDescription = stringResource(Res.string.navigation_icon_description),
                    )
                },
                label = { Text(text = screenRoute.name()) },
                selected = selected,
                unselectedContentColor = LocalContentColor.current
                    .copy(alpha = if (enabled) ContentAlpha.high else ContentAlpha.disabled),
                selectedContentColor = MaterialTheme.colors.primary,
                enabled = enabled,
                onClick = {
                    // 現在の画面のルートと異なる場合のみ遷移する
                    if (!selected) {
                        navigateTo(screenRoute)
                    }
                },
            )
        }
    }
}

@Preview
@Composable
private fun BottomNavBarPreview() {
    AssemblePCTheme {
        BottomNavBar(
            currentRoute = ScreenRoute.TopScreen,
            enabled = true,
            navigateTo = {},
        )
    }
}