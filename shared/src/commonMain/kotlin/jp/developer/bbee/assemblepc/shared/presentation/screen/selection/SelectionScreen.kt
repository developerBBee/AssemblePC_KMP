package jp.developer.bbee.assemblepc.shared.presentation.screen.selection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import jp.developer.bbee.assemblepc.shared.presentation.ScreenRoute
import jp.developer.bbee.assemblepc.shared.presentation.common.LaunchedEffectUnitWithLog
import jp.developer.bbee.assemblepc.shared.presentation.screen.selection.components.VariableButtonsRow
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SelectionScreen(
    onNavigate: (ScreenRoute) -> Unit,
    viewModel: SelectionViewModel = koinViewModel(),
) {
    LaunchedEffectUnitWithLog(tag = "SelectionScreen") {
        viewModel.navigationSideEffect.collect { sideEffect ->
            when (sideEffect) {
                SelectionSideEffect.NextScreen ->
                    onNavigate(ScreenRoute.DeviceScreen)

                SelectionSideEffect.Error ->
                    onNavigate(ScreenRoute.TopScreen)
            }
        }
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
