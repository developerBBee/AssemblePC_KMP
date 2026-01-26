package jp.developer.bbee.assemblepc.shared.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import jp.developer.bbee.assemblepc.shared.presentation.common.BaseLayout
import jp.developer.bbee.assemblepc.shared.presentation.screen.assembly.AssemblyScreen
import jp.developer.bbee.assemblepc.shared.presentation.screen.device.DeviceScreen
import jp.developer.bbee.assemblepc.shared.presentation.screen.selection.SelectionScreen
import jp.developer.bbee.assemblepc.shared.presentation.screen.top.TopScreen
import jp.developer.bbee.assemblepc.shared.presentation.theme.AssemblePCTheme
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun AssemblePCApp(
    modifier: Modifier = Modifier,
    viewModel: AppViewModel = koinViewModel(),
) {
    val backStack = rememberNavBackStack()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val composition = (uiState as? AppUiState.Selected)?.composition

    val currentRoute: ScreenRoute? = backStack.lastOrNull()

    AssemblePCTheme {
        BaseLayout(
            modifier = modifier,
            currentRoute = currentRoute,
            composition = composition,
            navigateTo = { route -> backStack.navigateSingle(route) }
        ) {
            NavDisplay(
                backStack = backStack,
                onBack = { backStack.removeLastOrNull() },
                modifier = Modifier.fillMaxSize(),
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
                }
            )
        }
    }
}
