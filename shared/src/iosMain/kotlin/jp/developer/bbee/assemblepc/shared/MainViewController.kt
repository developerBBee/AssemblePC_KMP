package jp.developer.bbee.assemblepc.shared

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import jp.developer.bbee.assemblepc.shared.di.initializeKoin
import jp.developer.bbee.assemblepc.shared.presentation.AssemblePCApp

fun MainViewController() = ComposeUIViewController(
    configure = { initializeKoin() }
) {
    AssemblePCApp(
        modifier = Modifier
            .padding(WindowInsets.systemBars.asPaddingValues())
    )
}
