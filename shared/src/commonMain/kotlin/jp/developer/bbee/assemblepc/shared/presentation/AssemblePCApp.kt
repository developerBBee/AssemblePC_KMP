package jp.developer.bbee.assemblepc.shared.presentation

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import jp.developer.bbee.assemblepc.shared.presentation.common.BaseLayout
import jp.developer.bbee.assemblepc.shared.presentation.common.LaunchedEffectUnitWithLog
import jp.developer.bbee.assemblepc.shared.presentation.screen.assembly.AssemblyScreen
import jp.developer.bbee.assemblepc.shared.presentation.screen.device.DeviceScreen
import jp.developer.bbee.assemblepc.shared.presentation.screen.selection.SelectionScreen
import jp.developer.bbee.assemblepc.shared.presentation.screen.top.TopScreen
import jp.developer.bbee.assemblepc.shared.presentation.theme.AssemblePCTheme
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.koin.compose.viewmodel.koinViewModel

private val CONFIGURATION = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(ScreenRoute.TopScreen::class, ScreenRoute.TopScreen.serializer())
            subclass(ScreenRoute.SelectionScreen::class, ScreenRoute.SelectionScreen.serializer())
            subclass(ScreenRoute.DeviceScreen::class, ScreenRoute.DeviceScreen.serializer())
            subclass(ScreenRoute.AssemblyScreen::class, ScreenRoute.AssemblyScreen.serializer())
        }
    }
}

private val NONE_TRANSFORM = ContentTransform(
    targetContentEnter = EnterTransition.None,
    initialContentExit = ExitTransition.None,
)

@Composable
fun AssemblePCApp(
    modifier: Modifier = Modifier,
    viewModel: AppViewModel = koinViewModel(),
) {
    val backStack = rememberNavBackStack(CONFIGURATION, ScreenRoute.TopScreen)

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val composition = (uiState as? AppUiState.Selected)?.composition

    val currentRoute = backStack.lastOrNull() as? ScreenRoute

    LaunchedEffectUnitWithLog(tag = "AssemblePCApp")

    AssemblePCTheme {
        BaseLayout(
            modifier = modifier,
            currentRoute = currentRoute,
            composition = composition,
            navigateTo = { route -> backStack.navigateSingle(route) },
        ) {
            NavDisplay(
                backStack = backStack,
                modifier = Modifier.fillMaxSize(),
                onBack = { backStack.removeLastOrNull() },
                transitionSpec = { NONE_TRANSFORM },
                popTransitionSpec = { NONE_TRANSFORM },
                predictivePopTransitionSpec = { NONE_TRANSFORM },
                entryProvider = entryProvider {
                    entry<ScreenRoute.TopScreen> {
                        TopScreen(
                            onNavigate = { route -> backStack.navigateSingle(route) }
                        )
                    }

                    entry<ScreenRoute.SelectionScreen> {
                        SelectionScreen(
                            onNavigate = { route -> backStack.navigateSingle(route) }
                        )
                    }

                    entry<ScreenRoute.DeviceScreen> {
                        DeviceScreen(
                            onNavigate = { route -> backStack.navigateSingle(route) }
                        )
                    }

                    entry<ScreenRoute.AssemblyScreen> {
                        AssemblyScreen()
                    }
                },
            )
        }
    }
}
