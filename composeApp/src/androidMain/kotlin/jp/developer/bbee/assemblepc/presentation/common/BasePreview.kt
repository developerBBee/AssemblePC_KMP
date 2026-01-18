package jp.developer.bbee.assemblepc.presentation.common

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import jp.developer.bbee.assemblepc.common.Constants
import jp.developer.bbee.assemblepc.presentation.ScreenRoute
import jp.developer.bbee.assemblepc.presentation.ui.theme.AssemblePCTheme

@Composable
fun BasePreview(content: @Composable () -> Unit) {
    AssemblePCTheme {
        BaseLayout(
            currentRoute = ScreenRoute.TopScreen,
            composition = Constants.COMPOSITION_SAMPLE,
            navigateTo = {}
        ) {
            content()
        }
    }
}

@Composable
fun BaseBGPreview(content: @Composable () -> Unit) {
    AssemblePCTheme {
        Surface(color = MaterialTheme.colors.background) {
            content()
        }
    }
}
