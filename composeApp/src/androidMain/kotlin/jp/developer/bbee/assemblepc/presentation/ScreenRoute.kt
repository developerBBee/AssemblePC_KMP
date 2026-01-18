package jp.developer.bbee.assemblepc.presentation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ManageSearch
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.navOptions
import jp.developer.bbee.assemblepc.R
import jp.developer.bbee.assemblepc.presentation.ScreenRoute.AssemblyScreen
import jp.developer.bbee.assemblepc.presentation.ScreenRoute.DeviceScreen
import jp.developer.bbee.assemblepc.presentation.ScreenRoute.SelectionScreen
import jp.developer.bbee.assemblepc.presentation.ScreenRoute.TopScreen
import kotlinx.serialization.Serializable

@Serializable
sealed class ScreenRoute(@param:StringRes val resourceId: Int) {
    @Serializable
    data object TopScreen : ScreenRoute(R.string.top_screen)

    @Serializable
    data object SelectionScreen : ScreenRoute(R.string.selection_screen)

    @Serializable
    data object DeviceScreen : ScreenRoute(R.string.device_screen)

    @Serializable
    data object AssemblyScreen : ScreenRoute(R.string.assembly_screen)

    fun getIcon(): ImageVector = when (this) {
        is TopScreen -> Icons.Default.Home
        is SelectionScreen -> Icons.Default.Category
        is DeviceScreen -> Icons.AutoMirrored.Filled.ManageSearch
        is AssemblyScreen -> Icons.Default.Build
    }

    @Composable
    fun name(): String = stringResource(resourceId)
}

val ROUTE_LIST = listOf(
    TopScreen,
    SelectionScreen,
    DeviceScreen,
    AssemblyScreen
)

fun NavBackStackEntry.toScreenRoute(): ScreenRoute? {
    return ROUTE_LIST.firstOrNull { destination.hasRoute(it::class) }
}

fun NavController.navigateSingle(screenRoute: ScreenRoute) {
    popBackStack(screenRoute, true)

    val options = navOptions {
        launchSingleTop = true
    }
    navigate(screenRoute, options)
}
