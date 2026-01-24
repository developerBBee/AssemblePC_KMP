package jp.developer.bbee.assemblepc.shared.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
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
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val composition = (uiState as? AppUiState.Selected)?.composition

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute: ScreenRoute? = backStackEntry?.toScreenRoute()

    AssemblePCTheme {
        BaseLayout(
            modifier = modifier,
            currentRoute = currentRoute,
            composition = composition,
            navigateTo = { route -> navController.navigateSingle(route) }
        ) {
            NavHost(
                navController = navController,
                startDestination = ScreenRoute.TopScreen,
                modifier = Modifier.fillMaxSize(),
            ) {
                composable<ScreenRoute.TopScreen> {
                    TopScreen(
                        navController = navController,
                        scope = scope
                    )
                }

                composable<ScreenRoute.SelectionScreen> {
                    SelectionScreen(
                        navController = navController,
                        scope = scope
                    )
                }

                composable<ScreenRoute.DeviceScreen> {
                    DeviceScreen(
                        navController = navController,
                        scope = scope
                    )
                }

                composable<ScreenRoute.AssemblyScreen> {
                    AssemblyScreen()
                }
            }
        }
    }
}
