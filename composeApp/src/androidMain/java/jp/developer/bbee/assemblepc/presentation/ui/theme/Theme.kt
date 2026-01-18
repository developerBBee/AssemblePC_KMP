package jp.developer.bbee.assemblepc.presentation.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
    primary = AquaBlue,
    primaryVariant = NavyBlue,
    onPrimary = Color.White,
    secondary = PaleBlue,
    onSecondary = Color.Black,
    surface = PaleWhite,
    onSurface = PaleBlack,
)

@Composable
fun AssemblePCTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}