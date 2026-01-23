package jp.developer.bbee.assemblepc.shared.presentation.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import jp.developer.bbee.assemblepc.shared.domain.model.Composition
import jp.developer.bbee.assemblepc.shared.presentation.ScreenRoute
import jp.developer.bbee.assemblepc.shared.presentation.components.BottomNavBar
import jp.developer.bbee.assemblepc.shared.presentation.components.HeaderInfoBar

@Composable
fun BaseLayout(
    modifier: Modifier = Modifier,
    currentRoute: ScreenRoute?,
    composition: Composition?,
    navigateTo: (ScreenRoute) -> Unit,
    content: @Composable () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            HeaderInfoBar(
                composition = composition
            )
        },
        bottomBar = {
            BottomNavBar(
                currentRoute = currentRoute,
                composition = composition,
                navigateTo = navigateTo
            )
        }
    ) { innerPadding ->
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier
                .padding(paddingValues = innerPadding),
            color = MaterialTheme.colors.background
        ) {
            content()
        }
    }
}
