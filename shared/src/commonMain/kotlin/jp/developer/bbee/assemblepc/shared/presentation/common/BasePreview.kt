package jp.developer.bbee.assemblepc.shared.presentation.common

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import jp.developer.bbee.assemblepc.shared.common.Constants
import jp.developer.bbee.assemblepc.shared.presentation.ScreenRoute
import jp.developer.bbee.assemblepc.shared.presentation.theme.AssemblePCTheme

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
