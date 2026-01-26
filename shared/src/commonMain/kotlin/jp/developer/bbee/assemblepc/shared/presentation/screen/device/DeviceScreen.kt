package jp.developer.bbee.assemblepc.shared.presentation.screen.device

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import assemblepc.shared.generated.resources.Res
import assemblepc.shared.generated.resources.label_device_selected
import assemblepc.shared.generated.resources.network_error_message
import jp.developer.bbee.assemblepc.shared.presentation.common.BasePreview
import jp.developer.bbee.assemblepc.shared.common.Constants
import jp.developer.bbee.assemblepc.shared.domain.model.Device
import jp.developer.bbee.assemblepc.shared.presentation.ScreenRoute
import jp.developer.bbee.assemblepc.shared.presentation.components.AssemblyDialog
import jp.developer.bbee.assemblepc.shared.presentation.screen.device.components.DeviceRow
import jp.developer.bbee.assemblepc.shared.presentation.screen.device.components.DeviceSearchText
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DeviceScreen(
    onNavigate: (ScreenRoute) -> Unit,
    deviceViewModel: DeviceViewModel = koinViewModel(),
) {

    LaunchedEffect(Unit) {
        deviceViewModel.navigationSideEffect.collect {
            onNavigate(ScreenRoute.AssemblyScreen)
        }
    }
    val uiState by deviceViewModel.uiState.collectAsStateWithLifecycle()
    val dialogUiState by deviceViewModel.dialogUiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        when (val state = uiState) {
            is DeviceUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            is DeviceUiState.Error -> {
                val builder = StringBuilder(stringResource(Res.string.network_error_message))
                if (state.error?.isNotEmpty() == true) {
                    builder.append("\n\n").append(state.error)
                }
                Text(
                    text = builder.toString(),
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )
            }

            is DeviceUiState.Success -> {
                DeviceSearchText(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .fillMaxWidth()
                        .height(60.dp),
                    currentSearchText = state.searchText,
                    onSearchChanged = deviceViewModel::searchDevice,
                    currentSortType = state.currentDeviceSort,
                    onSortChanged = deviceViewModel::changeSortType,
                )

                DeviceItems(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    selectedDevices = state.selectedDevices,
                    unselectedDevices = state.visibleDevices
                        .filterNot {
                            state.selectedDevices
                                .map { d -> d.device }
                                .contains(it)
                        },
                    onSelectedDeviceClick = deviceViewModel::notifyDeviceSelected,
                    onUnselectedDeviceClick = deviceViewModel::notifyDeviceSelected,
                )
            }
        }
    }

    dialogUiState?.let { (deviceQty, isEdit) ->
        AssemblyDialog(
            isEdit = isEdit,
            quantity = deviceQty.quantity,
            device = deviceQty.device,
            onDismiss = deviceViewModel::clearDialog,
            onAddAssembly = deviceViewModel::addAssembly,
            onDeleteAssembly = deviceViewModel::deleteAssembly,
        )
    }
}

@Composable
private fun DeviceItems(
    modifier: Modifier = Modifier,
    selectedDevices: List<DeviceWithQty>,
    unselectedDevices: List<Device>,
    onSelectedDeviceClick: (DeviceWithQty) -> Unit,
    onUnselectedDeviceClick: (Device) -> Unit,
) {
    BoxWithConstraints(modifier = modifier) {
        val halfHeight: Dp = maxHeight * 0.36f

        Column(modifier = Modifier.fillMaxSize()) {

            selectedDevices.takeIf { it.isNotEmpty() }?.let { devices ->
                Column(
                    modifier = Modifier.heightIn(max = halfHeight)
                        .background(MaterialTheme.colors.primaryVariant)
                        .padding(4.dp)
                ) {
                    Text(
                        text = stringResource(Res.string.label_device_selected),
                        color = Color.White,
                    )

                    LazyColumn(
                        modifier = Modifier
                            .background(MaterialTheme.colors.background)
                            .testTag("selected_parts_list")
                    ) {
                        items(devices) { deviceQty ->
                            DeviceRow(device = deviceQty.device, quantity = deviceQty.quantity) {
                                onSelectedDeviceClick(deviceQty)
                            }
                        }
                    }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("unselected_parts_list")
            ) {
                items(unselectedDevices) { device ->
                    DeviceRow(device = device) {
                        onUnselectedDeviceClick(device)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun DeviceItemsPreview() {
    BasePreview {
        DeviceItems(
            modifier = Modifier.fillMaxSize(),
            selectedDevices = List(5) { DeviceWithQty(Constants.DEVICE_SAMPLE, 1) },
            unselectedDevices = List(10) { Constants.DEVICE_SAMPLE },
            onSelectedDeviceClick = {},
            onUnselectedDeviceClick = {},
        )
    }
}

@Preview
@Composable
private fun DeviceItemsEmptySelectedPreview() {
    BasePreview {
        DeviceItems(
            modifier = Modifier.fillMaxSize(),
            selectedDevices = emptyList(),
            unselectedDevices = List(10) { Constants.DEVICE_SAMPLE },
            onSelectedDeviceClick = {},
            onUnselectedDeviceClick = {},
        )
    }
}
