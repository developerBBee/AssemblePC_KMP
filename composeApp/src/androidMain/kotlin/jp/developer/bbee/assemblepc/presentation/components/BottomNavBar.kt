package jp.developer.bbee.assemblepc.presentation.components

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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import jp.developer.bbee.assemblepc.R
import jp.developer.bbee.assemblepc.common.Constants
import jp.developer.bbee.assemblepc.domain.model.Composition
import jp.developer.bbee.assemblepc.presentation.ROUTE_LIST
import jp.developer.bbee.assemblepc.presentation.ScreenRoute
import jp.developer.bbee.assemblepc.presentation.ui.theme.AssemblePCTheme

@Composable
fun BottomNavBar(
    currentRoute: ScreenRoute?,
    composition: Composition?,
    navigateTo: (ScreenRoute) -> Unit,
) {
    BottomNavigation(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.surface,
    ) {
        ROUTE_LIST.forEach { screenRoute ->
            BottomNavigationItem(
                modifier = Modifier.testTag(screenRoute.javaClass.simpleName),
                icon = {
                    Icon(
                        imageVector = screenRoute.getIcon(),
                        contentDescription = stringResource(R.string.navigation_icon_description),
                    )
                },
                label = { Text(text = screenRoute.name()) },
                selected = currentRoute == screenRoute,
                // TopScreenの場合は他のアイコン色を無効色にする
                unselectedContentColor = if (currentRoute == ScreenRoute.TopScreen) {
                    LocalContentColor.current.copy(alpha = ContentAlpha.disabled)
                } else {
                    LocalContentColor.current.copy(alpha = ContentAlpha.high)
                },
                selectedContentColor = MaterialTheme.colors.primary,
                enabled = composition != null,
                onClick = {
                    currentRoute?.let { currentRoute ->
                        // 現在の画面のルートと異なる場合のみ遷移する
                        if (currentRoute != screenRoute) {
                            navigateTo(screenRoute)
                        }
                    }
                }
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
            composition = Constants.COMPOSITION_SAMPLE,
            navigateTo = {}
        )
    }
}