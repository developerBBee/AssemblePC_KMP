package jp.developer.bbee.assemblepc.presentation.screen.selection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import jp.developer.bbee.assemblepc.presentation.ScreenRoute
import jp.developer.bbee.assemblepc.presentation.navigateSingle
import jp.developer.bbee.assemblepc.presentation.screen.selection.components.VariableButtonsRow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SelectionScreen(
    navController: NavController,
    scope: CoroutineScope,
    viewModel: SelectionViewModel = hiltViewModel(),
) {

    DisposableEffect(Unit) {
        val job = scope.launch {
            viewModel.navigationSideEffect.collect { sideEffect ->
                when (sideEffect) {
                    SelectionSideEffect.NextScreen ->
                        navController.navigateSingle(ScreenRoute.DeviceScreen)

                    SelectionSideEffect.Error ->
                        navController.navigateSingle(ScreenRoute.TopScreen)
                }
            }
        }

        onDispose { job.cancel() }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
        ) {
            VariableButtonsRow(onSelected = viewModel::saveCurrentDeviceType)
        }
    }
}
