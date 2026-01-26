package jp.developer.bbee.assemblepc.shared.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import assemblepc.shared.generated.resources.Res
import assemblepc.shared.generated.resources.assembly_screen
import assemblepc.shared.generated.resources.build
import assemblepc.shared.generated.resources.category
import assemblepc.shared.generated.resources.device_screen
import assemblepc.shared.generated.resources.home
import assemblepc.shared.generated.resources.manage_search
import assemblepc.shared.generated.resources.selection_screen
import assemblepc.shared.generated.resources.top_screen
import jp.developer.bbee.assemblepc.shared.presentation.ScreenRoute.AssemblyScreen
import jp.developer.bbee.assemblepc.shared.presentation.ScreenRoute.DeviceScreen
import jp.developer.bbee.assemblepc.shared.presentation.ScreenRoute.SelectionScreen
import jp.developer.bbee.assemblepc.shared.presentation.ScreenRoute.TopScreen
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Serializable
sealed interface ScreenRoute : NavKey {
    @Serializable
    data object TopScreen : ScreenRoute

    @Serializable
    data object SelectionScreen : ScreenRoute

    @Serializable
    data object DeviceScreen : ScreenRoute

    @Serializable
    data object AssemblyScreen : ScreenRoute

    @Composable
    fun getIcon(): ImageVector = when (this) {
        is TopScreen -> Res.drawable.home
        is SelectionScreen -> Res.drawable.category
        is DeviceScreen -> Res.drawable.manage_search
        is AssemblyScreen -> Res.drawable.build
    }.let { res -> vectorResource(res) }

    @Composable
    fun name(): String = when (this) {
        is TopScreen -> Res.string.top_screen
        is SelectionScreen -> Res.string.selection_screen
        is DeviceScreen -> Res.string.device_screen
        is AssemblyScreen -> Res.string.assembly_screen
    }.let { res -> stringResource(res) }
}

val ROUTE_LIST: List<ScreenRoute> = listOf(
    TopScreen,
    SelectionScreen,
    DeviceScreen,
    AssemblyScreen,
)

fun MutableList<ScreenRoute>.navigateSingle(route: ScreenRoute) {
    removeAll { it == route }
    add(route)
}

@Composable
fun rememberNavBackStack(
    startDestination: ScreenRoute = TopScreen
): SnapshotStateList<ScreenRoute> {
    return rememberSaveable(
        saver = listSaver(
            save = { list -> list.map { it.toIndex() } },
            restore = { indices -> mutableStateListOf(*indices.map { it.toRoute() }.toTypedArray()) },
        )
    ) {
        mutableStateListOf(startDestination)
    }
}

private fun ScreenRoute.toIndex(): Int = ROUTE_LIST.indexOf(this)

private fun Int.toRoute(): ScreenRoute = ROUTE_LIST[this]
