package jp.developer.bbee.assemblepc.shared.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ManageSearch
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.navOptions
import assemblepc.shared.generated.resources.Res
import assemblepc.shared.generated.resources.assembly_screen
import assemblepc.shared.generated.resources.device_screen
import assemblepc.shared.generated.resources.selection_screen
import assemblepc.shared.generated.resources.top_screen
import jp.developer.bbee.assemblepc.shared.presentation.ScreenRoute.AssemblyScreen
import jp.developer.bbee.assemblepc.shared.presentation.ScreenRoute.DeviceScreen
import jp.developer.bbee.assemblepc.shared.presentation.ScreenRoute.SelectionScreen
import jp.developer.bbee.assemblepc.shared.presentation.ScreenRoute.TopScreen
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource

@Serializable
sealed interface ScreenRoute {
    @Serializable
    data object TopScreen : ScreenRoute

    @Serializable
    data object SelectionScreen : ScreenRoute

    @Serializable
    data object DeviceScreen : ScreenRoute

    @Serializable
    data object AssemblyScreen : ScreenRoute

    fun getIcon(): ImageVector = when (this) {
        is TopScreen -> Icons.Default.Home
        is SelectionScreen -> Icons.Default.Category
        is DeviceScreen -> Icons.AutoMirrored.Filled.ManageSearch
        is AssemblyScreen -> Icons.Default.Build
    }

    @Composable
    fun name(): String = when (this) {
        is TopScreen -> Res.string.top_screen
        is SelectionScreen -> Res.string.selection_screen
        is DeviceScreen -> Res.string.device_screen
        is AssemblyScreen -> Res.string.assembly_screen
    }.let { res -> stringResource(res) }
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
