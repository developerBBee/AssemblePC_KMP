package jp.developer.bbee.assemblepc.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import jp.developer.bbee.assemblepc.BuildConfig
import jp.developer.bbee.assemblepc.presentation.ScreenRoute.AssemblyScreen
import jp.developer.bbee.assemblepc.presentation.ScreenRoute.DeviceScreen
import jp.developer.bbee.assemblepc.presentation.ScreenRoute.SelectionScreen
import jp.developer.bbee.assemblepc.presentation.ScreenRoute.TopScreen
import jp.developer.bbee.assemblepc.presentation.common.BaseLayout
import jp.developer.bbee.assemblepc.presentation.screen.assembly.AssemblyScreen
import jp.developer.bbee.assemblepc.presentation.screen.device.DeviceScreen
import jp.developer.bbee.assemblepc.presentation.screen.selection.SelectionScreen
import jp.developer.bbee.assemblepc.presentation.screen.top.TopScreen
import jp.developer.bbee.assemblepc.presentation.ui.theme.AssemblePCTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDark = isSystemInDarkTheme()
            LaunchedEffect(isDark) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        android.graphics.Color.TRANSPARENT,
                        android.graphics.Color.TRANSPARENT,
                    ) { false },
                    navigationBarStyle = SystemBarStyle.auto(
                        android.graphics.Color.TRANSPARENT,
                        android.graphics.Color.TRANSPARENT,
                    ) { false },
                )
            }

            AssemblePCApp(
                modifier = Modifier
                    .padding(WindowInsets.systemBars.asPaddingValues())
                    .semantics { testTagsAsResourceId = BuildConfig.DEBUG }
            )
        }
    }
}

@Composable
private fun AssemblePCApp(
    modifier: Modifier = Modifier,
    viewModel: AppViewModel = hiltViewModel()
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
                startDestination = TopScreen,
                modifier = Modifier.fillMaxSize(),
            ) {
                composable<TopScreen> {
                    TopScreen(
                        navController = navController,
                        scope = scope
                    )
                }

                composable<SelectionScreen> {
                    SelectionScreen(
                        navController = navController,
                        scope = scope
                    )
                }

                composable<DeviceScreen> {
                    DeviceScreen(
                        navController = navController,
                        scope = scope
                    )
                }

                composable<AssemblyScreen> {
                    AssemblyScreen()
                }
            }
        }
    }
}
