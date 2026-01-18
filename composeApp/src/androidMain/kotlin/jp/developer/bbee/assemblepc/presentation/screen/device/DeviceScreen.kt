package jp.developer.bbee.assemblepc.presentation.screen.device

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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import jp.developer.bbee.assemblepc.R
import jp.developer.bbee.assemblepc.presentation.common.BasePreview
import jp.developer.bbee.assemblepc.common.Constants
import jp.developer.bbee.assemblepc.domain.model.Device
import jp.developer.bbee.assemblepc.presentation.ScreenRoute
import jp.developer.bbee.assemblepc.presentation.navigateSingle
import jp.developer.bbee.assemblepc.presentation.components.AssemblyDialog
import jp.developer.bbee.assemblepc.presentation.screen.device.components.DeviceRow
import jp.developer.bbee.assemblepc.presentation.screen.device.components.DeviceSearchText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DeviceScreen(
    navController: NavController,
    scope: CoroutineScope,
    deviceViewModel: DeviceViewModel = hiltViewModel(),
) {

    DisposableEffect(Unit) {
        val job = scope.launch {
            deviceViewModel.navigationSideEffect.collect {
                navController.navigateSingle(ScreenRoute.AssemblyScreen)
            }
        }

        onDispose { job.cancel() }
    }
    val uiState by deviceViewModel.uiState.collectAsStateWithLifecycle()
    val dialogUiState by deviceViewModel.dialogUiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        when (val state = uiState) {
            DeviceUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            is DeviceUiState.Error -> {
                val builder = StringBuilder(stringResource(id = R.string.network_error_message))
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
                    Text(text = stringResource(R.string.label_device_selected), color = Color.White)

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
