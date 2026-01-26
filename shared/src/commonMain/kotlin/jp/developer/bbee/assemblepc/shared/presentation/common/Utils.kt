package jp.developer.bbee.assemblepc.shared.presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope

internal expect fun getScreenWidthDp(): Int

@Composable
internal fun LaunchedEffectUnitWithLog(
    tag: String,
    block: suspend CoroutineScope.() -> Unit = {},
) {
    LaunchedEffect(Unit) {
        Napier.d(message = "Launched", tag = tag)
        block()
    }
}